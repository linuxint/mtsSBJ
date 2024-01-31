package com.devkbil.mtssbj.admin.organ;

import com.devkbil.mtssbj.common.tree.TreeMaker;
import com.devkbil.mtssbj.common.util.FileUtil;
import com.devkbil.mtssbj.common.util.FileVO;
import com.devkbil.mtssbj.common.util.UtilEtc;
import com.devkbil.mtssbj.config.security.AdminAuthorize;
import com.devkbil.mtssbj.etc.EtcService;
import com.devkbil.mtssbj.member.UserVO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@AdminAuthorize
public class UserController {

    @Autowired
    private DeptService deptService;

    @Autowired
    private UserService userService;

    @Autowired
    private EtcService etcService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    /**
     * 리스트.
     */
    @RequestMapping(value = "/adUser")
    public String user(HttpServletRequest request, ModelMap modelMap) {
        String userno = request.getSession().getAttribute("userno").toString();

        etcService.setCommonAttribute(userno, modelMap);

        List<?> listview = deptService.selectDept();

        TreeMaker tm = new TreeMaker();
        String treeStr = tm.makeTreeByHierarchy(listview);

        modelMap.addAttribute("treeStr", treeStr);

        return "admin/organ/User";
    }

    /**
     * User 리스트.
     */
    @RequestMapping(value = "/adUserList")
    public String userList(HttpServletRequest request, ModelMap modelMap) {
        String deptno = request.getParameter("deptno");

        return commonUserList(modelMap, deptno);
    }

    /**
     * 지정된 부서의 사용자 리스트.
     */
    public String commonUserList(ModelMap modelMap, String deptno) {

        List<?> listview = userService.selectUserList(deptno);

        modelMap.addAttribute("listview", listview);

        return "admin/organ/UserList";
    }

    /**
     * 사용자 저장.
     * 신규 사용자는 저장 전에 중복 확인
     */
    @RequestMapping(value = "/adUserSave")
    public String userSave(HttpServletResponse response, ModelMap modelMap, UserVO userInfo) {

        if (userInfo.getUserno() == null || "".equals(userInfo.getUserno())) {
            String userid = userService.selectUserID(userInfo.getUserid());
            if (userid != null) {
                return "common/blank";
            }
        }
        FileUtil fs = new FileUtil();
        FileVO fileInfo = fs.saveFile(userInfo.getPhotofile());
        if (fileInfo != null) {
            userInfo.setPhoto(fileInfo.getRealname());
        }
        userInfo.setUserpw(passwordEncoder.encode(userInfo.getUserpw()));
        userService.insertUser(userInfo);

        return commonUserList(modelMap, userInfo.getDeptVO().getDeptno());
    }

    /**
     * ID 중복 확인.
     */
    @RequestMapping(value = "/chkUserid")
    public void chkUserid(HttpServletRequest request, HttpServletResponse response) {
        String userid = request.getParameter("userid");

        userid = userService.selectUserID(userid);

        UtilEtc.responseJsonValue(response, userid);
    }

    /**
     * 사용자 조회.
     */
    @RequestMapping(value = "/adUserRead")
    public void userRead(HttpServletRequest request, HttpServletResponse response) {
        String userno = request.getParameter("userno");

        UserVO userInfo = userService.selectUserOne(userno);

        UtilEtc.responseJsonValue(response, userInfo);
    }

    /**
     * 사용자 삭제.
     */
    @RequestMapping(value = "/adUserDelete")
    public String userDelete(HttpServletRequest request, ModelMap modelMap, UserVO userInfo) {

        userService.deleteUser(userInfo.getUserno());

        return commonUserList(modelMap, userInfo.getDeptVO().getDeptno());
    }
}

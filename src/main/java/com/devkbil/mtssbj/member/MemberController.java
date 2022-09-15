package com.devkbil.mtssbj.member;

import com.devkbil.mtssbj.admin.organ.UserService;
import com.devkbil.mtssbj.common.FileVO;
import com.devkbil.mtssbj.common.SearchVO;
import com.devkbil.mtssbj.common.UtilEtc;
import com.devkbil.mtssbj.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


@Controller
public class MemberController {
    @Autowired
    private UserService userService;
    
    @Autowired
    private MemberService memberService;
    
    /**
     * 내정보.
     */
    @RequestMapping(value = "/memberForm")
    public String memberForm(HttpServletRequest request, ModelMap modelMap) {
        String save = request.getParameter("save");
        
        String userno = request.getSession().getAttribute("userno").toString();
        
        UserVO userInfo = userService.selectUserOne(userno);
        
        modelMap.addAttribute("userInfo", userInfo);
        modelMap.addAttribute("save", save);
        
        return "member/memberForm";
    }
    
    /**
     * 사용자 저장.
     */
    @RequestMapping(value = "/userSave")
    public String userSave(HttpServletRequest request, ModelMap modelMap, UserVO userInfo) {
        String userno = request.getSession().getAttribute("userno").toString();
        userInfo.setUserno(userno);
        
        FileUtil fs = new FileUtil();
        FileVO fileInfo = fs.saveImage(userInfo.getPhotofile());
        if(fileInfo != null) {
            userInfo.setPhoto(fileInfo.getRealname());
        }
        userService.updateUserByMe(userInfo);
        
        return "redirect:/memberForm?save=OK";
    }
    
    /**
     * 비밀번호 변경.
     */
    @RequestMapping(value = "/changePWSave")
    public void changePWSave(HttpServletRequest request, HttpServletResponse response, UserVO userInfo) {
        String userno = request.getSession().getAttribute("userno").toString();
        userInfo.setUserno(userno);
        
        userService.updateUserPassword(userInfo);
        
        UtilEtc.responseJsonValue(response, "OK");
    }
    
    /**
     * 직원조회.
     */
    @RequestMapping(value = "/searchMember")
    public String searchMember(SearchVO searchVO, ModelMap modelMap) {
        
        if(searchVO.getSearchKeyword() != null & !"".equals(searchVO.getSearchKeyword())) {
            searchVO.pageCalculate(memberService.selectSearchMemberCount(searchVO)); // startRow, endRow
            
            List<?> listview = memberService.selectSearchMemberList(searchVO);
            
            modelMap.addAttribute("listview", listview);
        }
        modelMap.addAttribute("searchVO", searchVO);
        return "member/searchMember";
    }
}

package com.devkbil.mtssbj.admin.menu;

import com.devkbil.mtssbj.common.tree.TreeMaker;
import com.devkbil.mtssbj.common.util.UtilEtc;
import com.devkbil.mtssbj.config.security.AdminAuthorize;
import com.devkbil.mtssbj.etc.EtcService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@AdminAuthorize
public class MenuController {

    @Autowired
    private MenuService menuService;

    @Autowired
    private EtcService etcService;

    /**
     * 리스트.
     */
    @RequestMapping(value = "/adMenuList")
    public String menuList(HttpServletRequest request, ModelMap modelMap) {
        String userno = request.getSession().getAttribute("userno").toString();

        etcService.setCommonAttribute(userno, modelMap);

        List<?> listview = menuService.selectMenu();

        TreeMaker tm = new TreeMaker();
        String treeStr = tm.makeTreeByHierarchy(listview);

        modelMap.addAttribute("treeStr", treeStr);

        return "admin/menu/MenuList";
    }

    /**
     * 메뉴 등록.
     */
    @RequestMapping(value = "/adMenuSave")
    public void menuSave(HttpServletRequest request, HttpServletResponse response, MenuVO menuInfo) {
        String userno = request.getSession().getAttribute("userno").toString();
        menuInfo.setReguserno(userno);

        menuService.insertMenu(menuInfo);

        UtilEtc.responseJsonValue(response, menuInfo);
    }

    /**
     * 메뉴 정보(하나).
     */
    @RequestMapping(value = "/adMenuRead")
    public void menuRead(HttpServletRequest request, HttpServletResponse response) {

        String mnuNo = request.getParameter("mnuNo");

        MenuVO menuVo = menuService.selectMenuOne(mnuNo);

        UtilEtc.responseJsonValue(response, menuVo);
    }

    /**
     * 메뉴 삭제.
     */
    @RequestMapping(value = "/adMenuDelete")
    public void menuDelete(HttpServletRequest request, HttpServletResponse response) {

        String mnuNo = request.getParameter("mnuNo");

        menuService.deleteMenu(mnuNo);

        UtilEtc.responseJsonValue(response, "OK");
    }

}

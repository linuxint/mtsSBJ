package com.devkbil.mtssbj.admin.board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.devkbil.mtssbj.common.tree.TreeMaker;
import com.devkbil.mtssbj.common.util.UtilEtc;
import com.devkbil.mtssbj.etc.EtcService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class BoardGroupController {

    @Autowired
    private BoardGroupService boardSvc;

    @Autowired
    private EtcService etcService;

    /**
     * 리스트.
     */
    @RequestMapping(value = "/adBoardGroupList")
    public String boardGroupList(HttpServletRequest request, ModelMap modelMap) {
        String userno = request.getSession().getAttribute("userno").toString();

        etcService.setCommonAttribute(userno, modelMap);

        List<?> listview = boardSvc.selectBoardGroupList();

        TreeMaker tm = new TreeMaker();
        String treeStr = tm.makeTreeByHierarchy(listview);

        modelMap.addAttribute("treeStr", treeStr);

        return "admin/board/BoardGroupList";
    }

    /**
     * 게시판 그룹 쓰기.
     */
    @RequestMapping(value = "/adBoardGroupSave")
    public void boardGroupSave(HttpServletResponse response, BoardGroupVO bgInfo) {

        boardSvc.insertBoard(bgInfo);
        UtilEtc.responseJsonValue(response, bgInfo);
    }

    /**
     * 게시판 그룹 읽기.
     */
    @RequestMapping(value = "/adBoardGroupRead")
    public void boardGroupRead(HttpServletRequest request, HttpServletResponse response) {

        String bgno = request.getParameter("bgno");

        BoardGroupVO bgInfo = boardSvc.selectBoardGroupOne(bgno);

        UtilEtc.responseJsonValue(response, bgInfo);
    }

    /**
     * 게시판 그룹 삭제.
     */
    @RequestMapping(value = "/adBoardGroupDelete")
    public void boardGroupDelete(HttpServletRequest request, HttpServletResponse response) {

        String bgno = request.getParameter("bgno");

        boardSvc.deleteBoardGroup(bgno);
        UtilEtc.responseJsonValue(response, "OK");

    }

}

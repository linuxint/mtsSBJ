package com.devkbil.admin.board;

import com.devkbil.common.TreeMaker;
import com.devkbil.common.UtilEtc;
import com.devkbil.etc.EtcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class BoardGroupController {

    static final Logger LOGGER = LoggerFactory.getLogger(BoardGroupController.class);

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

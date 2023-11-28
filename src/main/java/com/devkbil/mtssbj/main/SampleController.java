package com.devkbil.mtssbj.main;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.devkbil.mtssbj.board.BoardSearchVO;
import com.devkbil.mtssbj.board.BoardService;
import com.devkbil.mtssbj.common.ExcelConstant;
import com.devkbil.mtssbj.common.MakeExcel;
import com.devkbil.mtssbj.common.util.DateUtil;
import com.devkbil.mtssbj.etc.EtcService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class SampleController {

    @Autowired
    private SampleService sampleService;

    @Autowired
    private EtcService etcService;

    @Autowired
    private BoardService boardService;

    /**
     * 조직도/사용자 선택 샘플.
     */
    @RequestMapping(value = "/sample1")
    public String sample1(HttpServletRequest request, ModelMap modelMap) {
        String userno = request.getSession().getAttribute("userno").toString();

        etcService.setCommonAttribute(userno, modelMap);

        return "main/sample1";
    }

    /**
     * 날짜 선택 샘플.
     */
    @RequestMapping(value = "/sample2")
    public String sample2(HttpServletRequest request, ModelMap modelMap) {
        String userno = request.getSession().getAttribute("userno").toString();

        etcService.setCommonAttribute(userno, modelMap);
        // -----------------------------------------

        String today = DateUtil.date2Str(DateUtil.getToday());

        modelMap.addAttribute("today", today);
        return "main/sample2";
    }

    /**
     * 챠트 사용 샘플.
     */
    @RequestMapping(value = "/sample3")
    public String sample3(HttpServletRequest request, ModelMap modelMap) {
        String userno = request.getSession().getAttribute("userno").toString();

        etcService.setCommonAttribute(userno, modelMap);
        // -----------------------------------------

        List<?> listview = sampleService.selectBoardGroupCount4Statistic();
        modelMap.addAttribute("listview", listview);

        return "main/sample3";
    }

    /**
     * List & Excel 사용 샘플.
     */
    @RequestMapping(value = "/sample4")
    public String sample4(HttpServletRequest request, BoardSearchVO searchVO, ModelMap modelMap) {
        String userno = request.getSession().getAttribute("userno").toString();

        etcService.setCommonAttribute(userno, modelMap);
        // -----------------------------------------

        searchVO.pageCalculate(boardService.selectBoardCount(searchVO)); // startRow, endRow
        List<?> listview = boardService.selectBoardList(searchVO);

        modelMap.addAttribute("searchVO", searchVO);
        modelMap.addAttribute("listview", listview);

        return "main/sample4";
    }

    /**
     * List & Excel 사용 샘플.
     * Excel 생성 및 다운로드.
     */
    @RequestMapping(value = "/sample4Excel")
    public void sample4Excel(HttpServletRequest request, HttpServletResponse response, BoardSearchVO searchVO) {

        // bigData header
        String[] cellHeader = {"No", "그룹no", "그룹명", "글No", "글제목", "작성자명", "내용", "작성일", "작성시간", "조회수", "삭제여부", "파일수", "댓글수", "사용자번호", "사용자명", "공지여부", "좋아요"};

        // 게시판은 페이징 처리를 하지만 엑셀은 모든 데이터를 다운로드
        List<?> listview = boardService.selectBoardList(searchVO);
        Map<String, Object> beans = new HashMap<String, Object>();

        beans.put(ExcelConstant.DATA_KEY_NAME, listview);
        beans.put(ExcelConstant.SHEET_KEY_NAME, "data");
        beans.put(ExcelConstant.HEADER_KEY_NAME, cellHeader);

        MakeExcel me = new MakeExcel();
        // me.download(response, beans, me.get_Filename("mts")); // bigData download
        me.download(request, response, beans, me.get_Filename("mts"), "board2.xlsx"); // formatData download

    }
}

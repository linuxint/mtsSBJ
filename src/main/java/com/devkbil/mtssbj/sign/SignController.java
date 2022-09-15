package com.devkbil.mtssbj.sign;

import com.devkbil.mtssbj.admin.sign.SignDocService;
import com.devkbil.mtssbj.admin.sign.SignDocTypeVO;
import com.devkbil.mtssbj.common.SearchVO;
import com.devkbil.mtssbj.etc.EtcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class SignController {
    
    static final Logger LOGGER = LoggerFactory.getLogger(SignController.class);
    @Autowired
    private SignService signService;
    @Autowired
    private SignDocService signDocService;
    @Autowired
    private EtcService etcService;
    
    /**
     * 결제 받을 문서 리스트.
     */
    @RequestMapping(value = "/signListTobe")
    public String signListTobe(HttpServletRequest request, SearchVO searchVO, ModelMap modelMap) {
        // 페이지 공통: alert
        String userno = request.getSession().getAttribute("userno").toString();
        
        etcService.setCommonAttribute(userno, modelMap);
        
        // 
        searchVO.setUserno(userno);
        searchVO.pageCalculate(signService.selectSignDocTobeCount(searchVO)); // startRow, endRow
        List<?> listview = signService.selectSignDocTobeList(searchVO);
        
        modelMap.addAttribute("searchVO", searchVO);
        modelMap.addAttribute("listview", listview);
        
        return "sign/SignDocListTobe";
    }
    
    /**
     * 결제 할 문서 리스트.
     */
    @RequestMapping(value = "/signListTo")
    public String signListTo(HttpServletRequest request, SearchVO searchVO, ModelMap modelMap) {
        // 페이지 공통: alert
        String userno = request.getSession().getAttribute("userno").toString();
        
        etcService.setCommonAttribute(userno, modelMap);
        
        //
        if(searchVO.getSearchExt1() == null || "".equals(searchVO.getSearchExt1())) searchVO.setSearchExt1("sign");
        searchVO.setUserno(userno);
        searchVO.pageCalculate(signService.selectSignDocCount(searchVO)); // startRow, endRow
        List<?> listview = signService.selectSignDocList(searchVO);
        
        modelMap.addAttribute("searchVO", searchVO);
        modelMap.addAttribute("listview", listview);
        
        return "sign/SignDocList";
    }
    
    /**
     * 기안하기.
     */
    @RequestMapping(value = "/signDocTypeList")
    public String signDocTypeList(HttpServletRequest request, SearchVO searchVO, ModelMap modelMap) {
        // 페이지 공통: alert
        String userno = request.getSession().getAttribute("userno").toString();
        
        etcService.setCommonAttribute(userno, modelMap);
        
        List<?> listview = signDocService.selectSignDocTypeList(searchVO);
        
        modelMap.addAttribute("listview", listview);
        
        return "sign/SignDocTypeList";
    }
    
    @RequestMapping(value = "/signDocForm")
    public String signDocForm(HttpServletRequest request, SignDocVO signDocInfo, ModelMap modelMap) {
        // 페이지 공통: alert
        String userno = request.getSession().getAttribute("userno").toString();
        
        etcService.setCommonAttribute(userno, modelMap);
        
        // 개별 작업
        List<?> signlist = null;
        if(signDocInfo.getDocno() == null) {    // 신규
            signDocInfo.setDocstatus("1");
            SignDocTypeVO docType = signDocService.selectSignDocTypeOne(signDocInfo.getDtno());
            signDocInfo.setDtno(docType.getDtno());
            signDocInfo.setDoccontents(docType.getDtcontents());
            signDocInfo.setUserno(userno);
            // 사번, 이름, 기안/합의/결제, 직책
            signlist = signService.selectSignLast(signDocInfo);
            String signPath = "";
            for (int i = 0; i < signlist.size(); i++) {
                SignVO svo = (SignVO) signlist.get(i);
                signPath += svo.getUserno() + "," + svo.getUsernm() + "," + svo.getSstype() + "," + svo.getUserpos() + "||";
            }
            signDocInfo.setDocsignpath(signPath);
        } else {                                // 수정
            signDocInfo = signService.selectSignDocOne(signDocInfo);
            signlist = signService.selectSign(signDocInfo.getDocno());
        }
        modelMap.addAttribute("signDocInfo", signDocInfo);
        modelMap.addAttribute("signlist", signlist);
        
        return "sign/SignDocForm";
    }
    
    /**
     * 저장.
     */
    @RequestMapping(value = "/signDocSave")
    public String signDocSave(HttpServletRequest request, SignDocVO signDocInfo, ModelMap modelMap) {
        String userno = request.getSession().getAttribute("userno").toString();
        signDocInfo.setUserno(userno);
        
        signService.insertSignDoc(signDocInfo);
        
        return "redirect:/signListTobe";
    }
    
    /**
     * 읽기.
     */
    @RequestMapping(value = "/signDocRead")
    public String signDocRead(HttpServletRequest request, SignDocVO SignDocVO, ModelMap modelMap) {
        // 페이지 공통: alert
        String userno = request.getSession().getAttribute("userno").toString();
        
        etcService.setCommonAttribute(userno, modelMap);
        
        // 개별 작업
        
        SignDocVO signDocInfo = signService.selectSignDocOne(SignDocVO);
        List<?> signlist = signService.selectSign(signDocInfo.getDocno());
        String signer = signService.selectCurrentSigner(SignDocVO.getDocno());
        
        modelMap.addAttribute("signDocInfo", signDocInfo);
        modelMap.addAttribute("signlist", signlist);
        modelMap.addAttribute("signer", signer);
        
        return "sign/SignDocRead";
    }
    
    /**
     * 삭제.
     */
    @RequestMapping(value = "/signDocDelete")
    public String signDocDelete(HttpServletRequest request, SignDocVO SignDocVO) {
        
        signService.deleteSignDoc(SignDocVO);
        
        return "redirect:/signListTobe";
    }
    
    /**
     * 결재.
     */
    @RequestMapping(value = "/signSave")
    public String signSave(HttpServletRequest request, SignVO signInfo) {
        
        signService.updateSign(signInfo);
        
        return "redirect:/signListTo";
    }
    
    /**
     * 회수.
     */
    @RequestMapping(value = "/signDocCancel")
    public String signDocCancel(HttpServletRequest request, String docno) {
        signService.updateSignDocCancel(docno);
        
        return "redirect:/signListTobe";
    }
}

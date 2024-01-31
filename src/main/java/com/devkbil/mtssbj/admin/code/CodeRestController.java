package com.devkbil.mtssbj.admin.code;

import com.devkbil.mtssbj.search.SearchVO;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/code")
public class CodeRestController {

    @Autowired
    private CodeService codeService;

    /**
     * [API] 코드 등록
     *
     * @return ResponseEntity<Integer> : 응답 결과 및 응답 코드 반환
     */
    @PostMapping("/codeList")
    public ResponseEntity<List<?>> codeList(@RequestBody @Valid CodeVO codeVO) {
        log.debug("코드를 조회합니다..");
        SearchVO searchVO = new SearchVO();
        searchVO.setRowStart(1);
        searchVO.setDisplayRowCount(100);

        List<?> resultList = codeService.selectCodeList(searchVO);

        return new ResponseEntity<>(resultList, HttpStatus.OK);
    }
}

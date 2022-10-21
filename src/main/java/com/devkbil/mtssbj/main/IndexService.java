package com.devkbil.mtssbj.main;

import com.devkbil.mtssbj.common.ExtFieldVO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IndexService {
    
    @Autowired
    private SqlSessionTemplate sqlSession;
    
    
    public List<?> selectRecentNews() {
        return sqlSession.selectList("selectRecentNews");
    }
    
    public List<?> selectTimeLine() {
        return sqlSession.selectList("selectTimeLine");
    }
    
    public List<?> selectNoticeListTop5() {
        return sqlSession.selectList("selectNoticeListTop5");
    }
    
    public List<?> selectSchList4Calen(ExtFieldVO param) {
        return sqlSession.selectList("selectSchList4Calen", param);
    }
    
}

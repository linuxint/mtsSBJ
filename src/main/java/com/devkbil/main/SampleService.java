package com.devkbil.main;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SampleService {
    
    @Autowired
    private SqlSessionTemplate sqlSession;
    
    public List<?> selectBoardGroupCount4Statistic() {
        return sqlSession.selectList("selectBoardGroupCount4Statistic");
    }
}

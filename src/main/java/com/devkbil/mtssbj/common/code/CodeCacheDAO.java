package com.devkbil.mtssbj.common.code;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("commonCodeDAO")
public class CodeCacheDAO { // extends EgovComAbstractDAO

    @Autowired
    private SqlSessionTemplate sqlSession;
    //@Autowired(required = false)
    //private DataSourceTransactionManager txManager;

    /**
     * 공통코드그룹 List 조회
     *
     * @param
     * @return
     */
    public List<?> selectListCodeGroup() {
        return sqlSession.selectList("selectListCodeGroup");
    }

    /**
     * 공통코드상세 List 조회
     *
     * @param
     * @return
     */
    public List<?> selectListCode() {
        return sqlSession.selectList("selectListCode");
    }
}

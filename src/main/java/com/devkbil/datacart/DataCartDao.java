package com.devkbil.datacart;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class DataCartDao {
    
    @Autowired
    private SqlSessionTemplate sqlSession;
    void insertDataCart(DataCart dataCart) {
        sqlSession.insert("insertDataCart", dataCart);
    }
    List<?> selectDataCartList(String inspReqId) {
        return sqlSession.selectList("selectDataCartList", inspReqId);
    }
    void deleteDataCartOne(Map param) {
        sqlSession.update("deleteDataCartOne", param);
    }
    void deleteDataCart(String inspReqId) {
        sqlSession.update("deleteDataCart", inspReqId);
    }
    void cartCheckOut(String inspReqId) {
        sqlSession.insert("cartCheckOut", inspReqId);
    }
    /*
        void insertDataCart(DataCart dataCart) throws Exception;
    List<?> selectDataCartList(String inspReqId) throws Exception;
    void deleteDataCartOne(Map param) throws Exception;
    void deleteDataCart(String inspReqId) throws Exception;
    void cartCheckOut(String inspReqId) throws Exception;
     */
}

package com.devkbil.crud;

import com.devkbil.common.SearchVO;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.HashMap;
import java.util.List;

@Service
public class CrudService {
    
    @Autowired
    private SqlSessionTemplate sqlSession;
    @Autowired
    private DataSourceTransactionManager txManager;
    
    static final Logger LOGGER = LoggerFactory.getLogger(CrudService.class);
    
    /**
     * 리스트.
     */
    public Integer selectCrudCount(SearchVO param) {
        return sqlSession.selectOne("selectCrudCount", param);
    }
    
    public List<?> selectCrudList(SearchVO param) {
        return sqlSession.selectList("selectCrudList", param);
    }
    
    /**
     * 저장.
     */
    public void insertCrud(CrudVO param) {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = txManager.getTransaction(def);
        
        try {
            if(param.getCrno() == null || "".equals(param.getCrno())) {
                sqlSession.insert("insertCrud", param);
            } else {
                sqlSession.update("updateCrud", param);
            }
            txManager.commit(status);
        } catch (TransactionException ex) {
            txManager.rollback(status);
            LOGGER.error("insertCrud");
        }
    }
    
    /**
     * 읽기.
     */
    public CrudVO selectCrudOne(CrudVO param) {
        return sqlSession.selectOne("selectCrudOne", param);
    }
    
    /**
     * 삭제.
     */
    public void deleteCrud(CrudVO param) {
        sqlSession.update("deleteCrud", param);
    }
    
    /**
     * 저장.
     */
    public void deleteChk(String[] param) {
        HashMap hm = new HashMap();
        hm.put("list", param);
        
        sqlSession.insert("deleteChk", hm);
    }
}

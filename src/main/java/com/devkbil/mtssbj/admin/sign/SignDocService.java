package com.devkbil.mtssbj.admin.sign;

import com.devkbil.mtssbj.search.SearchVO;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.List;

@Slf4j
@Service
public class SignDocService {
    
    @Autowired
    private SqlSessionTemplate sqlSession;
    @Autowired(required = false)
    private DataSourceTransactionManager txManager;
    
    /**
     * 리스트.
     */
    public Integer selectSignDocTypeCount(SearchVO param) {
        return sqlSession.selectOne("selectSignDocTypeCount", param);
    }
    
    public List<?> selectSignDocTypeList(SearchVO param) {
        return sqlSession.selectList("selectSignDocTypeList", param);
    }
    
    /**
     * 저장.
     */
    public void insertSignDocType(SignDocTypeVO param) {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = txManager.getTransaction(def);
        
        try {
            if(param.getDtno() == null || "".equals(param.getDtno())) {
                sqlSession.insert("insertSignDocType", param);
            } else {
                sqlSession.update("updateSignDocType", param);
            }
            txManager.commit(status);
        } catch (TransactionException ex) {
            txManager.rollback(status);
            log.error("insertSignDocType");
        }
    }
    
    /**
     * 읽기.
     */
    public SignDocTypeVO selectSignDocTypeOne(String param) {
        return sqlSession.selectOne("selectSignDocTypeOne", param);
    }
    
    /**
     * 삭제.
     */
    public void deleteSignDocType(SignDocTypeVO param) {
        sqlSession.update("deleteSignDocType", param);
    }
    
}

package com.devkbil.mtssbj.admin.code;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.devkbil.mtssbj.search.SearchVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CodeService {

    @Autowired
    private SqlSessionTemplate sqlSession;
    @Autowired(required = false)
    private JpaTransactionManager txManager;

    public Integer selectCodeCount(SearchVO param) {
        return sqlSession.selectOne("selectCodeCount", param);
    }

    public List<?> selectCodeList(SearchVO param) {
        return sqlSession.selectList("selectCodeList", param);
    }

    /**
     * 글 저장.
     */
    public void insertCode(String codeFormType, CodeVO param) {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = txManager.getTransaction(def);

        try {
            if ("U".equals(codeFormType)) {
                sqlSession.update("updateCode", param);
            } else {
                sqlSession.insert("insertCode", param);
            }
            txManager.commit(status);
        } catch (TransactionException ex) {
            txManager.rollback(status);
            log.error("insertCode");
        }
    }

    public CodeVO selectCodeOne(CodeVO param) {
        return sqlSession.selectOne("selectCodeOne", param);
    }

    public void deleteCodeOne(CodeVO param) {
        sqlSession.delete("deleteCodeOne", param);
    }

}

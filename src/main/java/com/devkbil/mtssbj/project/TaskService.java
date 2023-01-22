package com.devkbil.mtssbj.project;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.devkbil.mtssbj.common.ExtFieldVO;
import com.devkbil.mtssbj.common.util.FileVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TaskService {

    @Autowired
    private SqlSessionTemplate sqlSession;
    @Autowired(required = false)
    private DataSourceTransactionManager txManager;

    /**
     * ------------------------------------------
     * Task.
     */
    public List<?> selectTaskList(String param) {
        return sqlSession.selectList("selectTaskList", param);
    }

    public List<?> selectTaskWorkerList(String param) {
        return sqlSession.selectList("selectTaskWorkerList", param);
    }

    /**
     * Task 저장.
     */
    public void insertTask(TaskVO param) {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = txManager.getTransaction(def);

        try {
            if (param.getTsno() == null || "".equals(param.getTsno())) {
                if ("".equals(param.getTsparent())) {
                    param.setTsparent(null);
                }
                sqlSession.insert("insertTask", param);
            } else {
                sqlSession.update("updateTask", param);
                sqlSession.delete("deleteTaskUser", param.getTsno());
            }
            String userno = param.getUserno();
            if (userno != null) {
                ExtFieldVO fld = new ExtFieldVO(param.getTsno(), null, null);
                String[] usernos = userno.split(",");
                for (int i = 0; i < usernos.length; i++) {
                    if ("".equals(usernos[i])) {
                        continue;
                    }
                    fld.setField2(usernos[i]);
                    sqlSession.update("insertTaskUser", fld);
                }
            }
            txManager.commit(status);
        } catch (TransactionException ex) {
            txManager.rollback(status);
            log.error("insertTask");
        }
    }

    public TaskVO selectTaskOne(String param) {
        return sqlSession.selectOne("selectTaskOne", param);
    }

    public List<?> selectTaskFileList(String param) {
        return sqlSession.selectList("selectTaskFileList", param);
    }

    public void deleteTaskOne(String param) {
        sqlSession.delete("deleteTaskOne", param);
    }

    /**
     * ------------------------------------------
     * TaskMine.
     */
    public List<?> selectTaskMineList(ExtFieldVO param) {
        return sqlSession.selectList("selectTaskMineList", param);
    }

    /**
     * TaskMine 저장.
     */
    public void insertTaskMine(TaskVO param, List<FileVO> filelist, String[] fileno) {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = txManager.getTransaction(def);

        try {
            sqlSession.update("updateTaskMine", param);

            if (fileno != null) {
                HashMap<String, String[]> fparam = new HashMap<String, String[]>();
                fparam.put("fileno", fileno);
                sqlSession.insert("deleteTaskFile", fparam);
            }

            for (FileVO f : filelist) {
                f.setParentPK(param.getTsno());
                sqlSession.insert("insertTaskFile", f);
            }

            txManager.commit(status);
        } catch (TransactionException ex) {
            txManager.rollback(status);
            log.error("insertTaskMine");
        }
    }

    /**
     * TaskMine 복사.
     */
    public void taskCopy(ExtFieldVO param) {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = txManager.getTransaction(def);

        try {
            sqlSession.insert("taskCopy_step1", param);
            sqlSession.update("taskCopy_step2", param.getField2());   // prno

            txManager.commit(status);
        } catch (TransactionException ex) {
            txManager.rollback(status);
            log.error("insertTaskMine");
        }
    }

}

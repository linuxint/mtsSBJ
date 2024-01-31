package com.devkbil.mtssbj.admin.organ;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeptService {

    @Autowired
    private SqlSessionTemplate sqlSession;

    public List<?> selectDept() {
        return sqlSession.selectList("selectDept");
    }

    /**
     * 부서저장.
     */
    public void insertDept(DeptVO param) {
        if ("".equals(param.getParentno())) {
            param.setParentno(null);
        }

        if (param.getDeptno() == null || "".equals(param.getDeptno())) {
            sqlSession.insert("insertDept", param);
        } else {
            sqlSession.insert("updateDept", param);
        }
    }

    public DeptVO selectDeptOne(String param) {
        return sqlSession.selectOne("selectDeptOne", param);
    }

    public void deleteDept(String param) {
        sqlSession.delete("deleteDept", param);
    }

}

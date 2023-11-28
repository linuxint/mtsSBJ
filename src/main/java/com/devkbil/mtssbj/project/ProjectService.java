package com.devkbil.mtssbj.project;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devkbil.mtssbj.common.ExtFieldVO;
import com.devkbil.mtssbj.search.SearchVO;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProjectService {

    @Autowired
    private SqlSessionTemplate sqlSession;

    /**
     * ------------------------------------------
     * 프로젝트.
     */
    public Integer selectProjectCount(SearchVO param) {
        return sqlSession.selectOne("selectProjectCount", param);
    }

    public List<?> selectProjectList(SearchVO param) {
        return sqlSession.selectList("selectProjectList", param);
    }

    /**
     * 프로젝트 저장.
     */
    public void insertProject(ProjectVO param) {
        if (param.getPrno() == null || "".equals(param.getPrno())) {
            sqlSession.insert("insertProject", param);
        } else {
            sqlSession.update("updateProject", param);
        }
    }

    public ProjectVO selectProjectOne(String param) {
        return sqlSession.selectOne("selectProjectOne", param);
    }

    /**
     * 프로젝트 수정/삭제 권한 확인.
     */
    public String selectProjectAuthChk(ProjectVO param) {
        return sqlSession.selectOne("selectProjectAuthChk", param);
    }

    public void updateProjectRead(ExtFieldVO param) {
        sqlSession.insert("updateProjectRead", param);
    }

    public void deleteProjectOne(String param) {
        sqlSession.delete("deleteProjectOne", param);
    }

}

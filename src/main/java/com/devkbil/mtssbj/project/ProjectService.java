package com.devkbil.mtssbj.project;

import com.devkbil.mtssbj.common.ExtFieldVO;
import com.devkbil.mtssbj.search.SearchVO;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {
    
    static final Logger LOGGER = LoggerFactory.getLogger(ProjectService.class);
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
        if(param.getPrno() == null || "".equals(param.getPrno())) {
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

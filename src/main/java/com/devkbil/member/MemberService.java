package com.devkbil.member;

import com.devkbil.common.SearchVO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {
    
    @Autowired
    private SqlSessionTemplate sqlSession;
    
    public Integer selectSearchMemberCount(SearchVO param) {
        return sqlSession.selectOne("selectSearchMemberCount", param);
    }
    
    public List<?> selectSearchMemberList(SearchVO param) {
        return sqlSession.selectList("selectSearchMemberList", param);
    }
    
    public UserVO selectMember4Login(LoginVO param) {
        return sqlSession.selectOne("selectMember4Login", param);
    }
    
    public void insertLogIn(String param) {
        sqlSession.insert("insertLogIn", param);
    }
    
    public void insertLogOut(String param) {
        sqlSession.insert("insertLogOut", param);
    }
    
}

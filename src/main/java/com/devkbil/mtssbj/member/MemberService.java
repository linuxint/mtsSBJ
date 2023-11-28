package com.devkbil.mtssbj.member;

import com.devkbil.mtssbj.repository.MemberRepository;
import com.devkbil.mtssbj.search.SearchVO;
import lombok.SneakyThrows;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {

    @Autowired
    private SqlSessionTemplate sqlSession;

    private final MemberRepository repository;

    public Integer selectSearchMemberCount(SearchVO param) {
        return sqlSession.selectOne("selectSearchMemberCount", param);
    }

    public List<?> selectSearchMemberList(SearchVO param) {
        return sqlSession.selectList("selectSearchMemberList", param);
    }

    @SneakyThrows
    public UserVO selectMember4Login(LoginVO param) {
        return sqlSession.selectOne("selectMember4Login", param);
    }

    public void insertLogIn(String param) {
        sqlSession.insert("insertLogIn", param);
    }

    public void insertLogOut(String param) {
        sqlSession.insert("insertLogOut", param);
    }


    @Autowired
    public MemberService(MemberRepository repository) {
        this.repository = repository;
    }

    public Optional<LoginVO> findOne(String userId) {
        return repository.findByUserid(userId);
    }
}

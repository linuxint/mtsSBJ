package com.devkbil.mtssbj.admin.organ;

import com.devkbil.mtssbj.member.UserVO;
import com.devkbil.mtssbj.search.SearchVO;
import lombok.SneakyThrows;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private SqlSessionTemplate sqlSession;
    public List<?> selectUserList(String param) {
        return sqlSession.selectList("selectUserList", param);
    }

    public List<?> selectUserListWithDept(SearchVO param) {
        return sqlSession.selectList("selectUserListWithDept", param);
    }

    /**
     * 사용자 저장.
     */
    public void insertUser(UserVO param) {
        if (param.getUserno() == null || "".equals(param.getUserno())) {
            sqlSession.insert("insertUser", param);
        } else {
            sqlSession.insert("updateUser", param);
        }
    }

    public String selectUserID(String param) {
        return sqlSession.selectOne("selectUserID", param);
    }

    public UserVO selectUserOne(String param) {
        return sqlSession.selectOne("selectUserOne", param);
    }

    public void deleteUser(String param) {
        sqlSession.delete("deleteUser", param);
    }

    /*
     * 사용자
     */
    public void updateUserByMe(UserVO param) {
        sqlSession.delete("updateUserByMe", param);
    }

    @SneakyThrows
    public void updateUserPassword(UserVO param) {
        sqlSession.delete("updateUserPassword", param);
    }
}

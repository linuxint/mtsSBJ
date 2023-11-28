package com.devkbil.mtssbj.admin.menu;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuService {

    @Autowired
    private SqlSessionTemplate sqlSession;

    public List<?> selectMenu() {
        return sqlSession.selectList("selectMenu");
    }

    /**
     * 부서저장.
     */
    public void insertMenu(MenuVO param) {
        if ("".equals(param.getMnuParent())) {
            param.setMnuParent(null);
        }

        if (param.getMnuNo() == null || "".equals(param.getMnuNo())) {
            sqlSession.insert("insertMenu", param);
        } else {
            sqlSession.insert("updateMenu", param);
        }
    }

    public MenuVO selectMenuOne(String param) {
        return sqlSession.selectOne("selectMenuOne", param);
    }

    public void deleteMenu(String param) {
        sqlSession.delete("deleteMenu", param);
    }

}

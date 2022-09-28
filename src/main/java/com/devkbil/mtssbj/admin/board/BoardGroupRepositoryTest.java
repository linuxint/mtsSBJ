package com.devkbil.mtssbj.admin.board;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
//@TestPropertySource(locations = "classpath:application-test.propertiess")
class BoardGroupRepositoryTest {
    
    @Autowired
    BoardGroupRepository boardGroupRepository;
    
    @Test
    @DisplayName("게시판그룹테스트")
    public void createBoardGroupTest() {
        BoardGroup bg = new BoardGroup();
        bg.setBgname("테스트1");
        bg.setBgnotice("Y");
        bg.setBgparent("1");
        bg.setBgreadonly("N");
        bg.setBgreply("Y");
        
        BoardGroup saveBg = boardGroupRepository.save(bg);
    
        System.out.println(saveBg.toString());
    }

    public void createBoardGroupList() {
        BoardGroup bg = new BoardGroup();
        BoardGroup saveBg = new BoardGroup();
        for(int i=1;i<=10;i++) {
            bg.setBgname("테스트 " + i);
            bg.setBgnotice("Y");
            bg.setBgparent("1");
            bg.setBgreadonly("N");
            bg.setBgreply("Y");
    
            saveBg = boardGroupRepository.save(bg);
            saveBg = new BoardGroup();
            bg = new BoardGroup();
        }
    }
    
    @Test
    @DisplayName("게시판그룹 조회 테스트 List10")
    public void findByBgnameTest() {
        createBoardGroupList();
        List<BoardGroup> boardGroupList = boardGroupRepository.findByBgname("테스트 1");
        for(BoardGroup boardGroup : boardGroupList) {
            System.out.println(boardGroup.toString());
        }
    }
}

package com.devkbil.mtssbj.admin.board;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardGroupRepository extends JpaRepository<BoardGroup, Long> {
    
    List<BoardGroup> findByBgname(String bgname);
}

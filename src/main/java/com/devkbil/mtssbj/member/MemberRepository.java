package com.devkbil.mtssbj.member;

import com.devkbil.mtssbj.member.UserVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<UserVO, Long> {

    Optional<UserVO> findByUserid(String userId);
}

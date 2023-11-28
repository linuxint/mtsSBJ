package com.devkbil.mtssbj.config.security;

import com.devkbil.mtssbj.member.LoginVO;
import com.devkbil.mtssbj.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class MyUserDetailService implements UserDetailsService {
    private final MemberService memberService;

    @Autowired
    public MyUserDetailService(MemberService memberService) {
        this.memberService = memberService;
    }

    @Override
    public UserDetails loadUserByUsername(String insertedUserId) throws UsernameNotFoundException {
        Optional<LoginVO> findOne = memberService.findOne(insertedUserId);
        LoginVO loginVO = findOne.orElseThrow(() -> new UsernameNotFoundException("없는 회원입니다 ㅠ"));

        return User.builder()
                .username(loginVO.getUserid())
                .password(loginVO.getUserpw())
                .roles(loginVO.getUserrole())
                .build();
    }
}

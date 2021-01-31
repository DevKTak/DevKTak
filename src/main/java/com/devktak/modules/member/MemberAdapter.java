package com.devktak.modules.member;

import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

/**
 * SpringSecurity가 다루는 User정보와
 * 도메인이 다루는 유저 정보의 사이를 매꾸는 일종의 어댑터 역할이라고 생각
 * Principal로 사용
 */
@Getter
public class MemberAdapter extends User {

    private Member member; // 도메인이 다루는 유저 정보

    public MemberAdapter(Member member) {
        // SpringSecurity가 가지고 있는 유저정보를 도메인이 다루는 유저 정보와 연동
        super(member.getUserId(), member.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_USER")));
        this.member = member;
    }
}

package com.devktak.modules.member;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME) // 런타임 까지도 유지가 되어야 한다
@Target(ElementType.PARAMETER) // 파라미터에 붙일 수 있도록 한다

/* 현재 이 어노테이션(@CurrentMember)을 참조하고 있는 객체가
    "anonymousUser" 라는 문자열이면 null 아니면 member 객체에다가 파라미터를 셋팅(주입) 해준다
    AccountService.java > login() , UserAccount.java를 참고 */

// MemberAdapter.java 에서 getMember로 객체 가져옴, member 이름 맞쳐줘야함
@AuthenticationPrincipal(expression = "#this == 'anonymousUser' ? null : member") // MemberAdapter.java 에서 getMember로 가져옴, member 이름 맞아야함
public @interface CurrentMember {}

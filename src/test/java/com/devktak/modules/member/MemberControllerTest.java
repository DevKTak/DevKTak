package com.devktak.modules.member;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Slf4j
@AutoConfigureMockMvc // MVC 테스트
class MemberControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private MemberRepository memberRepository;

    @DisplayName("회원 가입 화면 보이는지 테스트")
    @Test
    void signUpForm() throws Exception {
        mockMvc.perform(get("/sign-up"))
                .andDo(print()) // thymeleaf 이기 때문에 뷰템플릿 렌더링을 서블릿 컨테이너가 하지않고 뷰 생성을 해서 보내줌
                .andExpect(status().isOk()) // 200 떨어지는지
                .andExpect(view().name("member/sign-up")) // 뷰가 있는지
                .andExpect(model().attributeExists("signUpForm")) // addAttribute 키값으로 "signUpForm"이 있는지
                .andExpect(unauthenticated());
    }

//    @DisplayName("회원가입 처리 - 입력값 정상")
//    @Test
//    void signUpSubmit_with_correct_input() throws Exception {
//        mockMvc.
//    }
}
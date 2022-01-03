package com.devktak.modules.main;

import com.devktak.modules.member.MemberRepository;
import com.devktak.modules.member.MemberService;
import com.devktak.modules.member.form.SignUpForm;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
/* junit5를 사용할 때에는 RunWith 이런거 안써도됨
   @SpringBootTest에 이미 @ExtendWith이 달려 있기 때문 */
@SpringBootTest
@AutoConfigureMockMvc
        // MVC 테스트
class MainControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @BeforeEach // 한개의 테스트 시작 전
    void beforeEach() {
        SignUpForm signUpForm = new SignUpForm();
        signUpForm.setUserId("test11");
        signUpForm.setEmail("test11@test.com");
        signUpForm.setPassword("1234");
        memberService.signUp(signUpForm);
    }

    @AfterEach // 한개의 테스트 시작 후
    void afterEach() {
        memberRepository.deleteAll();
    }

//    @DisplayName("이메일로 로그인 성공")
//    @Test
//    void login_with_email() throws Exception {
//        mockMvc.perform(post("/login")
//                .param("username", "test11")
//                .param("password", "1234")
//                .with(csrf())) // form을 전달할 때 csrf 토큰도 같이 전송
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/")) // 로그인에 성공할 경우 "/" 루트
//                .andExpect(authenticated().withUsername("test11")); /* test11 이라는 username으로 인증이 될 것이다
//                                                                      MemberAdapter.java > super() 에서 첫번째 아규먼트인 username으로
//                                                                      userId를 줬기 때문에 userId로 인증한다 */
//    }











}
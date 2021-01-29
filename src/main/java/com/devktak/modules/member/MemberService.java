package com.devktak.modules.member;

import com.devktak.infra.config.AppProperties;
import com.devktak.infra.mail.EmailService;
import com.devktak.infra.mail.form.EmailMessageForm;
import com.devktak.modules.member.form.SignUpForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final MemberRepository memberRepository;
    private final AppProperties appProperties;
    private final TemplateEngine templateEngine; // 타임리프의 가장 핵심적인 클래스
    private final EmailService emailService;

    /** 회원 가입 **/
    public Member signUp(SignUpForm signUpForm) {
        Member newMember = memberSave(signUpForm); // 회원 저장
        sendSignupConfirmEmail(newMember); // 메일 전송

        return newMember;
    }

    /** 회원 저장 **/
    public Member memberSave(SignUpForm signUpForm) {
        signUpForm.setPassword(passwordEncoder.encode(signUpForm.getPassword())); // password BCrypt 인코딩
        Member member = modelMapper.map(signUpForm, Member.class);
        member.generateEmailCheckToken(); // 토큰 생성

        return memberRepository.save(member);
    }

    /** 메일 전송 **/
    private void sendSignupConfirmEmail(Member newMember) {
        Context context = new Context(); // model과 같은 역할
        context.setVariable("link", "/check-email-token?token=" + newMember.getEmailCheckToken() +
                "&email=" + newMember.getEmail());
        context.setVariable("name", newMember.getName());
        context.setVariable("linkName", "이메일 인증하기");
        context.setVariable("message", "DevKTak 서비스를 이용하려면 링크를 클릭하세요.");
        context.setVariable("host", appProperties.getHost());
        String message = templateEngine.process("mail/simple-link", context); // prefix :=> template / postfix :=> .html

        //== 메일 전송 폼 객체 생성 ==//
        EmailMessageForm emailMessageForm = EmailMessageForm.builder()
                .to(newMember.getEmail())
                .subject("DevKTak, 회원가입 인증")
                .message(message)
                .build();

        //== 메일 전송 ==//
        emailService.sendEmail(emailMessageForm);
    }

    /** 로그인 **/
    public void login(Member member) {
        // 로그인 할 때 토큰 생성하여 셋팅
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                // 첫번째 전달인자로 Principal을 넣어 준다, Principal로 Member 객체를 넣어서 @AuthenticationPrincipal (CurrentMember.java) 을 사용하기 위해 MemberAdapter 클래스를 만듬
                new MemberAdapter(member), // MemberAdapter를 Principal 객체로 썼음
                member.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_USER")));
        SecurityContextHolder.getContext().setAuthentication(token); // 만든 토큰값 넣기, view와 테스트에서 authenticated()로 이용할 수 있음
    }
}

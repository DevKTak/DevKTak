package com.devktak.modules.member.validator;

import com.devktak.modules.member.MemberRepository;
import com.devktak.modules.member.form.SignUpForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component // 아래에 private MemberRepository memberRepository; 이게 빈인데 빈을 주입받으려면 같이 빈이 되어야 하기때문에 등록
@RequiredArgsConstructor
public class SignUpFormValidator implements Validator {

    private final MemberRepository memberRepository;

    @Override
    /** 해당 타입의 객체를 지원하는지를 리턴한다 **/
    public boolean supports(Class<?> aClass) {
        return aClass.isAssignableFrom(SignUpForm.class); // SignUpForm 타입의 인스턴스를 검사할 것을 명시
    }

    @Override
    /** 검증하는 코드 구현하는 메소드 **/
    public void validate(Object object, Errors errors) {
        SignUpForm signUpForm = (SignUpForm)object;
        if (memberRepository.existsByEmail(signUpForm.getEmail())) {
            errors.rejectValue("email", "invalid.email", new Object[]{signUpForm.getEmail()}, "이미 사용중인 이메일 입니다.(BackEnd)");
        }

        if (memberRepository.existsByUserId(signUpForm.getUserId())) {
            errors.rejectValue("userId", "invalid.userId", new Object[]{signUpForm.getUserId()}, "이미 사용중인 아이디 입니다.(BackEnd)");
        }
    }
}

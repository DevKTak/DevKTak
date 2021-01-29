package com.devktak.modules.member;

import com.devktak.modules.member.form.SignUpForm;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.Errors;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Slf4j
@Controller
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/sign-up")
    public String signUpForm(Model model) {
        model.addAttribute(new SignUpForm());

        return "member/sign-up";
    }

    @PostMapping("/sign-up")
    public String signUp(@Valid @ModelAttribute SignUpForm signUpForm, Errors errors) {
        if (errors.hasErrors()) {
            log.info("errors ::: " + errors);
            for (ObjectError error: errors.getAllErrors()) {
                log.info("error.getDefaultMessage() = " + error.getDefaultMessage());
            }
            return "member/sign-up";
        }
        Member member = memberService.signUp(signUpForm); // 회원 가입
        memberService.login(member);

        return "redirect:/";

    }
}

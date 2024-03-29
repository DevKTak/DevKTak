package com.devktak.modules.member;

import com.devktak.modules.member.form.ProfileForm;
import com.devktak.modules.member.form.SignUpForm;
import com.devktak.modules.member.validator.SignUpFormValidator;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.validation.Errors;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Slf4j
@Controller
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberController {

    private final SignUpFormValidator signUpFormValidator;
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;

    /** 백엔드 커스텀 유효성 검사 (SignupFormValidator.java) 사용 **/
    @InitBinder("signUpForm") // signUp() 메소드 파라미터 같은 상황에서 SignUpForm을 받을 때 호출!, SignUpForm의 카멜케이스로 들어감
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(signUpFormValidator);
    }

    @GetMapping("/sign-up")
    public String signUpForm(Model model) {
        model.addAttribute(new SignUpForm());

        return "member/sign-up";
    }

    @PostMapping("/sign-up")
    public String signUp(@Valid @ModelAttribute SignUpForm signUpForm, Errors errors) {
        if (errors.hasErrors()) {
//            log.debug("errors ::: " + errors);
//            for (ObjectError error: errors.getAllErrors()) {
//                log.info("error.getDefaultMessage() = " + error.getDefaultMessage());
//            }
            for (FieldError error : errors.getFieldErrors()) {
                String validKeyName = String.format("valid_%s", error.getField());
                log.debug(validKeyName + " ::: " + error.getDefaultMessage());
            }

            return "member/sign-up";
        }
        Member member = memberService.signUp(signUpForm); // 회원 가입
        memberService.login(member);

        return "redirect:/";
    }

    @GetMapping("/check-email-token")
    public String checkEmailToken(String token, String email, Model model) {
        Member member = memberRepository.findByEmail(email);
        String view = "member/checked-email";

        if (member == null) {
            model.addAttribute("error", "wrong.email");
            return view;
        }

        // 리팩토링
//        if (!member.getEmailCheckToken().equals(token)) {
        if (!member.isValidToken(token)) {
            model.addAttribute("error", "wrong.token");
            return view;
        }

        // 리팩토링
//        account.setEmailVerified(true);
//        account.setJoinedAt(LocalDateTime.now());
        memberService.completeSignUp(member);

        model.addAttribute("numberOfUser", memberRepository.count());
        model.addAttribute("name", member.getName());
        return view;
    }

    /** email 인증 여부 체크하는 view로 핸들링 **/
    @GetMapping("/check-email")
    public String checkEmail(@CurrentMember Member member, Model model) {
        model.addAttribute("email", member.getEmail());
        return "member/check-email";
    }

    /** email 인증 재전송 **/
    @GetMapping("/resend-confirm-email")
    public String resendConfirmEmail(@CurrentMember Member member, Model model) {
        if (!member.canSendConfirmEmail()) {
            model.addAttribute("error", "인증 이메일은 1시간에 한번만 전송할 수 있습니다.");
            model.addAttribute("email", member.getEmail());
            return "member/check-email";
        } else {
            memberService.reGenerateEmailCheckToken(member); // 이메일 체크 토큰 재생성
            memberService.sendSignUpConfirmEmail(member); // 이메일 재전송
            return "redirect:/";
        }
    }

    @GetMapping("/profile-setting")
    public String profileSettingForm(@CurrentMember Member member, Model model) {
        model.addAttribute(member);
        model.addAttribute(modelMapper.map(member, ProfileForm.class));
        return "member/profile-setting";
    }

    /** 프로필 이미지 셋팅 **/
    @PostMapping("/profile-setting")
    public String profileSetting(@CurrentMember Member member, @Valid @ModelAttribute ProfileForm profileForm, Errors errors,
                                 Model model, RedirectAttributes attributes) {
        if (errors.hasErrors()) {
            /* 폼을 채웠던 Profile 데이터와 error에 대한 정보는 Model에 자동으로 들어가기 때문에
               GET 요청 때 처럼 다시 member 정보만 넣어주면 된다 */
            model.addAttribute(member);
            return "member/profile-setting";
        }
        model.addAttribute(member);
        memberService.updateProfile(member, profileForm);
        attributes.addFlashAttribute("message", "프로필 사진을 수정하였습니다.");

        return "redirect:/profile-setting"; // 사용자가 refresh 했을시 폼서브밋이 다시 일어나지 않도록 리다이렉트 시켜줌
    }
}

package com.devktak.modules.member.form;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter @Setter
public class SignUpForm {

    @NotBlank(message = "회원 ID는 필수입니다.")
//    @Length(min = 5, max = 20)
    @Pattern(regexp = "^[a-z0-9_-]{5,20}$", message = "공백없이 영문, 숫자 5자 ~ 20자 이내로 입력하세요.")
    private String userId;

    @Email
    @NotBlank(message = "email은 필수입니다.")
    private String email;

    @NotBlank
    @Length(min = 4, max = 16)
    private String password;

    @NotBlank
    @Length(min = 4, max = 16)
    private String passwordConfirm;

    @NotBlank
    private Long phoneNumber;

}
package com.devktak.modules.member.form;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Getter @Setter
@ToString
public class SignUpForm {

    @NotBlank(message = "회원 ID는 필수입니다.")
//    @Length(min = 5, max = 20)
    @Pattern(regexp = "^[a-z0-9_-]{5,20}$", message = "공백없이 영문, 숫자 5자 ~ 20자 이내로 입력하세요.")
    private String userId;

    @Email
    @NotBlank(message = "email은 필수입니다.")
    private String email;

    //    @NotEmpty // null과 ""을 허용하지 않는다
    @NotBlank // null과 ""와 " "(빈공백문자열)을 허용하지 않는다
    @Length(min = 4, max = 16)
    private String password;

    @NotBlank
    @Length(min = 10, max = 11)
    private String phoneNumber;

}
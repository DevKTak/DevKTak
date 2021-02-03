package com.devktak.modules.member;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter @Setter @EqualsAndHashCode(of = "id")
@Builder @AllArgsConstructor @NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Member {

    @Id @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String userId;

    @Column(unique = true)
    private String email;

    private String name;

    private String password;

    private String phoneNumber;

    private boolean emailVerified; // email 인증이 된 계정인지

    private String emailCheckToken; // email을 검증할 토큰값

    private LocalDateTime emailCheckTokenGeneratedAt; // emailCheckToken 생성 시간

    private LocalDateTime joinedAt;

    @Lob // String => varcahr(255) / @Lob == text
    @Basic(fetch = FetchType.EAGER) // 이미지 같은 경우는 유저를 로딩할때 종종 같이 쓰일거 같아서 FetchType.EAGER로 줬음
    private String profileImage;

    /** 이메일체크 랜덤 토큰 생성, 토큰 생성 시간 저장 **/
    public void generateEmailCheckToken() {
        this.emailCheckToken = UUID.randomUUID().toString();
        this.emailCheckTokenGeneratedAt = LocalDateTime.now();
    }

    /** 가입 인증 통과 시 **/
    public void completeSignUp() {
        this.emailVerified = true;
        this.joinedAt = LocalDateTime.now();
    }

    /** 토큰값 검증 **/
    public boolean isValidToken(String token) {
        // 쿼리스트링으로 넘어온 토큰과 회원가입 시 저장했던 토큰값 비교
        return this.emailCheckToken.equals(token);
    }

    /** 이메일 재전송 가능 여부 파악 **/
    public boolean canSendConfirmEmail() {
        // isBefore() : 두 개의 날짜와 시간 객체를 비교하여 현재 객체가 명시된 객체보다 앞선 시간인지를 비교함
        // 이메일체크 토큰 생성시간이 현재 시간에서 1시간 뺀것보다 앞선 시간인지.
        return this.emailCheckTokenGeneratedAt.isBefore(LocalDateTime.now().minusHours(1));
    }
}

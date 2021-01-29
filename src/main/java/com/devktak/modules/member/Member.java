package com.devktak.modules.member;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter @EqualsAndHashCode(of = "id")
@Builder @AllArgsConstructor @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String userId;

    @Column(unique = true)
    private String email;

    private String name;

    private String password;

    private String passwordConfirm;

    private Long phoneNumber;

    private boolean emailVerified; // email 인증이 된 계정인지

    private String emailCheckToken; // email을 검증할 토큰값

    private LocalDateTime emailCheckTokenGeneratedAt; // emailCheckToken 생성 시간

    private LocalDateTime joinedAt;

    @Lob
    @Basic(fetch = FetchType.EAGER) // String => varcahr(255) / @Lob == text
    // 이미지 같은 경우는 유저를 로딩할때 종종 같이 쓰일거 같아서 FetchType.EAGER로 줬음
    private String profileImage;

    /** 이메일체크 랜덤 토큰 생성, 토큰 생성 시간 저장 */
    public void generateEmailCheckToken() {
        this.emailCheckToken = UUID.randomUUID().toString();
        this.emailCheckTokenGeneratedAt = LocalDateTime.now();
    }
}

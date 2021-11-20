package com.devktak.infra.config;

import com.devktak.modules.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity // SpringSecurity 직접 설정
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter { // WebSecurity 설정을 좀 더 손쉽게 하기 위한 상속

  //    private final MemberService memberService; // UserDetailsService로 대체
  private final UserDetailsService userDetailsService;
  private final DataSource dataSource;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
       .mvcMatchers("sign-up", "check-email-token").permitAll() // 권한 확인없이 접근 해야 할 요청들
       .mvcMatchers(HttpMethod.GET, "/profile/*").permitAll() // 프로필 요청은 GET만 허용
       .anyRequest().authenticated(); // 나머지 요청은 로그인 (인증받은 사용자) 해야만 사용 가능

    http.formLogin() // Spring Security의 기본 로그인 화면
       .usernameParameter("userId") /* MemberAdapter.java 에서 super() 첫번째 인자로 기본값이 username 인데
                                                나는 userId를 사용하였기 때문에 해당 코드를 추가 해줘야 한다 */
       .loginPage("/login").permitAll(); // 커스텀한 페이지를 로그인 페이지로 사용

    http.logout() // post 방식 (/loguot) 자동 매핑
       .logoutSuccessUrl("/"); // 로그아웃 성공 시 url

    http.rememberMe() // 기본 세션 타임아웃 30분 이후에도 로그인 기억하기
       .userDetailsService(userDetailsService)
       .tokenRepository(tokenRepository()); // username, 토큰(랜덤, 매번 바뀜), 시리즈(랜덤, 고정) 3가지 조합해서 만든 토큰
  }

  /**
   * DB에서 토큰값을 가져와서 비교해야 하기 때문에 이 메소드 필요 (remember-me)
   */
  @Bean
  public PersistentTokenRepository tokenRepository() {
    JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
    jdbcTokenRepository.setDataSource(dataSource);
    return jdbcTokenRepository;
  }

  /**
   * 스태틱한 리소스들은 인증을 하지 않게(시큐리티 필터를 적용하지 말아라) 하는 로직
   */
  @Override
  public void configure(WebSecurity web) throws Exception {
    web.ignoring()
       .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
  }
}

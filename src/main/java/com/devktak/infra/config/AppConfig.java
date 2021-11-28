package com.devktak.infra.config;

import com.devktak.infra.interceptor.LogInterceptor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppConfig implements WebMvcConfigurer {

    @Value("${resource.path}")
    private String resourcePath;

    @Value("${upload.path")
    private String uploadPath;

  @Bean // BCrypt 인코딩을 위해 빈으로 등록
  public PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder(); // BCrypt 해싱 알고리즘 사용
  }

  @Bean // ModelMapper 사용을 위해 등록
  public ModelMapper modelMapper() {
    ModelMapper modelMapper = new ModelMapper();
    modelMapper.getConfiguration()
       .setMatchingStrategy(MatchingStrategies.STRICT); // 완전히 일치 할 경우에만 매칭 하는 설정
//                .setDestinationNameTokenizer(NameTokenizers.UNDERSCORE)
//                .setSourceNameTokenizer(NameTokenizers.UNDERSCORE);
    return modelMapper;
  }

  /**
   * 외부 경로 매핑
   **/
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler(uploadPath)
       .addResourceLocations(resourcePath);
  }

  /**
   * 로그 인터셉터
   **/
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new LogInterceptor())
       .order(1)
       .addPathPatterns("/**")
       .excludePathPatterns("/css/**", "*.ico", "/error");
  }
}

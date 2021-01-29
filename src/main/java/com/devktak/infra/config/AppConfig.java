package com.devktak.infra.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AppConfig {

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
}

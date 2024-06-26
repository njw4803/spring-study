package study.studyspring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.*;


/**
 * PasswordEncoder Config
 */
@Configuration // 스프링이 뜰 때  @Configuration 읽고 스프링 빈에 등록한다.
public class PasswordEncoderConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {

        /**
         * PasswordEncoder는 기본적으로 bcrypt 방식을 추천한다.
         * PasswordEncoder가 제공하는 방식으로 encode
         * 기본 전략 Bcrypt 방식으로 사용할 경우
        */
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    /*
    // 다른 암호화 방식으로 사용할 경우
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new Pbkdf2PasswordEncoder();
    }
    */


}

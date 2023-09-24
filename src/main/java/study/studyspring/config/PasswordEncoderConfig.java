package study.studyspring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

import java.util.HashMap;
import java.util.Map;

/**
 * PasswordEncoder Config
 */
@Configuration
public class PasswordEncoderConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {

        // 다른 암호화방식으로 변경하여 사용하기 위한 커스텀마이징
        // study.studyspring.config.PasswordEncoderProperties 에서 방식 설정 가능
        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put(PasswordEncoderProperties.ID, PasswordEncoderProperties.ENCODERS);

        return new DelegatingPasswordEncoder(PasswordEncoderProperties.ID, encoders);

        // 기본 기본 전략 Bcrypt 방식으로만 사용할 경우
        //return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    /*
    // Bcrypt 방식으로 사용할 경우 BCryptPasswordEncoder로 바로 사용해도 된다.
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    */

}

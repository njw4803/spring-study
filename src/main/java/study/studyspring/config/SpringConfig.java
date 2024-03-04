package study.studyspring.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import study.studyspring.repository.UserRepository;
import study.studyspring.service.UserService;

@Configuration // 스프링이 뜰 때  @Configuration 읽고 스프링 빈에 등록한다.
@RequiredArgsConstructor
public class SpringConfig {

    private final UserRepository userRepository;

    @Bean
    public UserService UserService() {
        return new UserService(userRepository);
    }


}

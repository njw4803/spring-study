package study.studyspring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import study.studyspring.repository.MemberRepository;
import study.studyspring.service.MemberService;

@Configuration // 스프링이 뜰 때  @Configuration 읽고 스프링 빈에 등록한다.
public class SpringConfig {

    private final MemberRepository memberRepository;

    @Autowired
    public SpringConfig(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Bean
    public BCryptPasswordEncoder encodePwd() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public MemberService memberService() {
        //return new MemberService(memberRepository());
        return new MemberService(memberRepository);
    }


}

package study.studyspring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

// 크로스 오리진 요청이 와도 허용(CORS)하는 설정, @CrossOrigin(인증이 필요 없는 경우 사용), 시큐리티 필터에 등록 (인증이 필요한 경우) , CORS 참고:https://inpa.tistory.com/entry/WEB-%F0%9F%93%9A-CORS-%F0%9F%92%AF-%EC%A0%95%EB%A6%AC-%ED%95%B4%EA%B2%B0-%EB%B0%A9%EB%B2%95-%F0%9F%91%8F
@Configuration // 스프링이 뜰 때  @Configuration 읽고 스프링 빈에 등록한다.
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() { // 필터에 등록해줘야한다.
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        // 내 서버가 응답을 할 때 json을 자바스크립트에서 처리할 수 있게 할지를 설정하는 것
        // config.setAllowCredentials(true); true로 설정하면 ajax,fetch 등 에서 요청하면 그 응답을 자바스크립트가 받을 수 있게 한다.
        // false로 하면 내 서버가 결정하는데 자바스크립트가 요청을 할 때 응답이 오지않는다.
        config.addAllowedOrigin("*"); // 모든 ip에 응답을 허용하겠다
        config.addAllowedHeader("*"); // 모든 header에 응답을 허용하겠다
        config.addAllowedMethod("*"); // 모든 post,get,delete,patch,put 요청을 허용하겠다
        source.registerCorsConfiguration("/api/**",config); // /api/** 로 들어오는 모든 주소는 이 config 설정을 따름
        return new CorsFilter(source);
    }
}

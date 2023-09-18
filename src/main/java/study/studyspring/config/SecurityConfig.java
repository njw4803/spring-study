package study.studyspring.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import study.studyspring.config.oauth.PrincipalOauth2UserService;

@Configuration
@EnableWebSecurity // 활성화, 스프링 시큐리티 필터(SecurityConfig)가 스프링 필터체인에 등록됨
@EnableGlobalMethodSecurity(securedEnabled = true)
// securedEnabled => secured 어노테이션 활성화
// prePostEnabled => preAuthorize, postAuthorize 어노테이션 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PrincipalOauth2UserService principalOauth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable(); //
        http.authorizeRequests()
                .antMatchers("/member/**").authenticated() // authenticated() 인증 후 접속 가능(인증만 되면 들어갈 수 있는 주소)
                .antMatchers("/premium/**").access("hasRole('P') or hasRole('V')") // .access() 인증 + 권한
                .antMatchers("/vip/**").access("hasRole('V')")// .access() 인증 + 권한
                .anyRequest().permitAll()// 다른 요청들은 인증없이 가능
                .and()
                .formLogin()
                .loginPage("/loginForm") // 권한이 없으면 로그인페이지로 이동
                // usernameParameter() => input 태그에서 name="username2"로 커스터마이징하기 위한 설정, PrincipalDetailsService에서 loadUserByUsername(String username2) 로 받아야 정상 작동
                .usernameParameter("id")
                .loginProcessingUrl("/login") // /login 주소가 호출이 되면 시큐리티가 낚아채서 대신 로그인을 진행
                .defaultSuccessUrl("/")
                .and()
                .oauth2Login()
                .loginPage("/loginForm") // google 로그인이 완료된 뒤의 후처리가 필요.
                // 1.코드받기(인증), 2.엑세스토큰(권한), 3.사용자프로필 정보를 가져옴,
                // 4-1. 그 정보를 토대로 회원가입 자동 진행
                .userInfoEndpoint()// Tip. 코드x, (엑세스토큰 + 사용자프로필정보 o)
                .userService(principalOauth2UserService);
        // 4-2 . (이메일,전화번호,이름,아이디), 추가 정보가 필요할 때 쇼핑몰 => (집주소), 백화점몰 => (vip등급,일반등급)

    }
}

/*
    CSRF
    Cross site Request forgery로 사이트 간 위조 요청인데,
    공격자가 인증된 브라우저에 저장된 쿠키의 세션 정보를 활용하여 웹 서버에 사용자가 의도하지 않은 요청을 전달하는 것으로
    즉 정상적인 사용자가 의도치 않은 위조요청을 보내는 것을 의미한다.
    예를 들어 A라는 도메인에서, 인증된 사용자 H가 위조된 request를 포함한 link, email을 사용하였을 경우(클릭, 또는 사이트 방문만으로도)
    , A 도메인에서는 이 사용자가 일반 유저인지, 악용된 공격인지 구분할 수가 없다.
    CSRF protection은 spring security에서 default로 설정된다.
    즉, protection을 통해 GET요청을 제외한 상태를 변화시킬 수 있는 POST, PUT, DELETE 요청으로부터 보호한다.
    csrf protection을 적용하였을 때, html에서 다음과 같은 csrf 토큰이 포함되어야 요청을 받아들이게 됨으로써, 위조 요청을 방지하게 됩니다.
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    Rest api에서의 CSRF
    spring security documentation에 non-browser clients 만을 위한 서비스라면 csrf를 disable 하여도 좋다고 한다.
    이 이유는 rest api를 이용한 서버라면, session 기반 인증과는 다르게 stateless하기 때문에 서버에 인증정보를 보관하지 않는다.
    rest api에서 client는 권한이 필요한 요청을 하기 위해서는 요청에 필요한 인증 정보를(OAuth2, jwt토큰 등)을 포함시켜야 한다.
    따라서 서버에 인증정보를 저장하지 않기 때문에 굳이 불필요한 csrf 코드들을 작성할 필요가 없다.
    rest api에서는 csrf 공격으로부터 안전하고 매번 api 요청으로부터 csrf 토큰을 받지 않아도 되어 이 기능을 disable() 하는 것이 더 좋은 판단

    https://velog.io/@wonizizi99/SpringSpring-security-CSRF%EB%9E%80-disable
    https://covenant.tistory.com/279
    https://velog.io/@woosim34/Spring-Spring-Security-%EC%84%A4%EC%A0%95-%EB%B0%8F-%EA%B5%AC%ED%98%84SessionSpring-boot3.0-%EC%9D%B4%EC%83%81
 */

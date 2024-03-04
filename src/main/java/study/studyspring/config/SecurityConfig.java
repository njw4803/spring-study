package study.studyspring.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.CorsFilter;
import study.studyspring.config.auth.jwt.JwtAuthenticationFilter;
import study.studyspring.config.auth.jwt.JwtAuthorizationFilter;
import study.studyspring.repository.UserRepository;

@Configuration // 스프링이 뜰 때  @Configuration 읽고 스프링 빈에 등록한다.
@EnableWebSecurity // 활성화, 스프링 시큐리티 필터(SecurityConfig)가 스프링 필터체인에 등록됨
@EnableGlobalMethodSecurity(securedEnabled = true) // securedEnabled => secured 어노테이션 활성화, prePostEnabled => preAuthorize, postAuthorize 어노테이션 활성화
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CorsFilter corsFilter;
    private final UserRepository userRepository;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();  // http.csrf() -> 정상적인 페이지에는 Csrf Token 값을 알려줘야 하는데 Tymeleaf에서는 페이지를 만들때 자동으로 Csrf Token을 넣어준다.
        //따로 추가하지 않았는데 아래와 같은 코드가 form tag안에 자동으로 생성된다.
        //대신 굳이 사용자에게 보여줄 필요가 없는 값이기 때문에 hidden으로 처리한다.rest api를 이용한 서버라면, session 기반 인증과는 다르게 stateless하기 때문에 서버에 인증정보를 보관하지 않기때문에 disable시킨다.
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //세션을 만드는 방식을 사용하진않음 설정
                .and()
                .addFilter(corsFilter) //크로스 오리진 요청이 와도 허용(CORS)하는 설정, @CrossOrigin(인증이 필요 없는 경우 사용), 시큐리티 필터에 등록 (인증이 필요한 경우) , CORS 참고:https://inpa.tistory.com/entry/WEB-%F0%9F%93%9A-CORS-%F0%9F%92%AF-%EC%A0%95%EB%A6%AC-%ED%95%B4%EA%B2%B0-%EB%B0%A9%EB%B2%95-%F0%9F%91%8F
                .formLogin().disable()
                .httpBasic().disable()
                // httpBasic().disable() -> headers에 Anthorization:ID,PW 담아서 요청하는 방식이 http Basic 인증 방식.
                // 매번 요청할 때마다 아이디랑 패스워드를 달고 요청. 쿠키에 세션을 만들 필요가 없음.
                // 확장성은 좋으나 아이디와 패스워드가 암호화가 안되기때문에 중간에 노출이 될 수 있다.
                // https를 사용하면 아이디와 패스워드가 암호화된다.
                // headers에 Anthorization:토큰(ID와 PW를 통해 만듬)을 넣는 방식(Bearer 방식)을 사용하기위해 http Basic방식을 disable 시킨다.
                // Bearer 방식 -> 토큰을 들고 가는 방식이어서 노출이 되면 안되지만 노출이 돼도 아이디랑 비밀번호를 노출되는건 아니기때문에 basic 방식보다는 안전하다.
                // 토큰은 로그인 할 때마다 서버쪽에서 다시 만들어주고 유효시간도 있기때문에 한 번 노출된다고 위험하진않다.
                // 토큰은 JWT(JSON WEB TOKEN) 사용, 세션 사용X
                .addFilter(new JwtAuthenticationFilter(authenticationManager())) // 꼭 전달해줘야하는 파라미터 AuthenticationManager. 로그인 컨트롤을 위해 UsernamePasswordAuthenticationFilter를 상속받은 JwtAuthenticationFilter객체를 넣어준다.
                .addFilter(new JwtAuthorizationFilter(authenticationManager(),userRepository))
                .authorizeRequests()
                .antMatchers("/","/test","api/v1/signUp","/loginForm","/token","/home","/error","/logout","/login").permitAll() // 인증없이 가능
                .antMatchers("/api/v1/premium/**").access("hasRole('PREMIUM') or hasRole('VIP') or hasRole('ADMIN')") // .access() 인증 + 권한
                .antMatchers("/api/v1/vip/**").access("hasRole('VIP') or hasRole('ADMIN')")// .access() 인증 + 권한
                .antMatchers("/api/v1/admin/**").access("hasRole('ADMIN')")// .access() 인증 + 권한
                .anyRequest().authenticated()// authenticated() 인증 후 접속 가능(인증만 되면 들어갈 수 있는 주소), 다른 요청들은 인증받은 사람만 가능
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/");
        http.anonymous().principal("anonymousUser");
        // 현재 사용자가 익명 사용자인지, 인증 사용자인지 구분할 수 있다는 장점
        // 비회원 인증(정상적인 경로로 접속하지않으면 비회원도 아니게 됨.)
        // AnonymousAuthenticationFilter 필터로 인해 시큐리티에서는 SecurityContextHolder.getContext().getAuthentication() 를 하더라도 항상 인증 객체가 있기에 비로그인 사용자(익명 사용자)를 체크하기 위해서는 Role 검사를 해야 한다.

    }

    /**
     * 특정 리소스에 대해서 SpringSecurity자체를 적용하지 않고 싶을 때 사용
     * http.authorizeRequests().requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll() 과 결과적으로는 같은 코드
     * 하지만 ignoring을 사용한 코드는 permitAll을 사용한 아래 코드와 다르게 아예 SpringSecurity의 대상에 포함되지 않는다.
     * 즉, 어떤 필터도 실행되지 않기 때문에 ignoring을 사용한 코드가 성능적으로 우수
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        // 정적 리소스 spring security 대상에서 제외
        //web.ignoring().antMatchers("/images/**", "/css/**"); // 아래 코드와 같은 코드
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
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

    CSRF 참고:https://velog.io/@wonizizi99/SpringSpring-security-CSRF%EB%9E%80-disable
    https://covenant.tistory.com/279
    https://velog.io/@woosim34/Spring-Spring-Security-%EC%84%A4%EC%A0%95-%EB%B0%8F-%EA%B5%AC%ED%98%84SessionSpring-boot3.0-%EC%9D%B4%EC%83%81
 */

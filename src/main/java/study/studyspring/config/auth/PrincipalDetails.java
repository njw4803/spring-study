package study.studyspring.config.auth;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import study.studyspring.domain.User;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

// 시큐리티가 /login 주소 요청이 오면 낚아채서 로그인을 진행시킨다.
// 로그인을 진행이 완료되면 시큐리티 session을 만들어준다.(Security ContextHolder)
// 오브젝트 => Authentication 타입 객체
// Authentication 안에 User 정보가 있어야됨.
// User 오브젝트 타입 => UserDetails 타입 객체
// Security Session => Authentication => UserDetails(PrincipalDetails)
@Getter
public class PrincipalDetails implements UserDetails, OAuth2User {

    private final User user;
    // 소셜 로그인 시 OAuth2 제공자로부터 받은 원본 attribute. 일반 로그인이면 null.
    private Map<String, Object> attributes;

    // 일반 로그인(JWT) 용 생성자
    public PrincipalDetails(User user) {
        this.user = user;
    }

    // 소셜 로그인(OAuth2) 용 생성자
    public PrincipalDetails(User user, Map<String, Object> attributes) {
        this.user = user;
        this.attributes = attributes;
    }

    // ===== OAuth2User =====
    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getName() {
        // OAuth2User 의 식별자. 로그인 id(소셜은 provider_providerId)를 사용한다.
        return user.getId();
    }

    //해당 user의 권한을 리턴하는 곳
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return user.getRole();
            }
        });
        return collect;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getName();
    }

    @Override
    public boolean isAccountNonExpired() { // 계정 유효기간 만료 여부
        return true;
    }

    @Override
    public boolean isAccountNonLocked() { // 계정 잠김 여부
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() { // 계정 비밀번호 오래사용 여부
        return true;
    }

    @Override
    public boolean isEnabled() { // 계정 활성화 여부

        // 우리 사이트에서 1년동안 회원이 로그인을 안하면 휴먼 계정으로 하기로함
        // 현재시간 - 로그인 시간 => 1년을 초과하면 retufn false;
        return true;
    }

}

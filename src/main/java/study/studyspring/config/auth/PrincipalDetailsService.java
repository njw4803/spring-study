package study.studyspring.config.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import study.studyspring.domain.User;
import study.studyspring.repository.UserRepository;

// 시큐리티 설정에서 loginProcessingUrl("/login");
// post방식으로 /login 요청해서 username,password를 전송하면 UsernamePasswordAuthenticationFilter 동작을 함
// /login 요청이 오면 자동으로 UserDetailsService 타입으로 IoC 되어있는 loadUserByUsername 함수가 실행
// formLogin().disable() 시키면 UsernamePasswordAuthenticationFilter 동작안하기 때문에
// UsernamePasswordAuthenticationFilter를 상속받은 필터(JwtAuthenticationFilter)를 만들어 시큐리티 설정파일에 등록해줘야한다.
@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {


    private final UserRepository userRepository;

    // loadUserByUsername() return 시 시큐리티 session(내부 Authentication(내부 UserDetails))에 들어간다.
    // 함수 종료 시 @AuthenticationPrincipal 어노테이션이 만들어진다.
    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {

        System.out.println("id = " + id);
        System.out.println("PrincipalDetailsService의 loadUserByUsername()");
        // Null이 아니라 값이 있으면 동작
        User userEntity = userRepository.findById(id);
        if (userEntity != null) {
            return new PrincipalDetails(userEntity);
        }
        return null;
    }

}

package study.studyspring.config.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import study.studyspring.domain.Member;
import study.studyspring.repository.MemberRepository;

import java.util.Optional;

import static org.springframework.data.repository.util.ClassUtils.ifPresent;

// 시큐리티 설정에서 loginProcessingUrl("/login");
// /login 요청이 오면 자동으로 UserDetailsService 타입으로 IoC 되어있는 loadUserByUsername 함수가 실행
@Service
public class PrincipalDetailsService implements UserDetailsService {

    @Autowired
    private MemberRepository memberRepository;

    // loadUserByUsername() return 시 시큐리티 session(내부 Authentication(내부 UserDetails))에 들어간다.
    // 함수 종료 시 @AuthenticationPrincipal 어노테이션이 만들어진다.
    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {

        // Null이 아니라 값이 있으면 동작
        memberRepository.findById(id)
                .ifPresent(PrincipalDetails::new);
        return null;
    }

}

package study.studyspring.config.oauth;

import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import study.studyspring.config.auth.PrincipalDetails;
import study.studyspring.config.oauth.provider.FacebookUserInfo;
import study.studyspring.config.oauth.provider.GoogleUserInfo;
import study.studyspring.config.oauth.provider.NaverUserInfo;
import study.studyspring.config.oauth.provider.OAuth2UserInfo;
import study.studyspring.domain.User;
import study.studyspring.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 소셜 로그인(google/facebook/naver) 후 제공자 프로필을 받아 회원 upsert 후 principal 반환
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauth2User = super.loadUser(userRequest);
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        OAuth2UserInfo oAuth2UserInfo;
        switch (registrationId) {
            case "google":
                oAuth2UserInfo = new GoogleUserInfo(oauth2User.getAttributes());
                break;
            case "facebook":
                oAuth2UserInfo = new FacebookUserInfo(oauth2User.getAttributes());
                break;
            case "naver":
                // 네이버는 응답이 response 키 안에 중첩되어 있다.
                @SuppressWarnings("unchecked")
                Map<String, Object> response = (Map<String, Object>) oauth2User.getAttributes().get("response");
                oAuth2UserInfo = new NaverUserInfo(response);
                break;
            default:
                throw new OAuth2AuthenticationException("지원하지 않는 소셜 로그인입니다: " + registrationId);
        }

        // provider + "_" + providerId 를 로그인 id 로 사용 (일반 회원가입 id 와 충돌 방지)
        String loginId = oAuth2UserInfo.getProvider() + "_" + oAuth2UserInfo.getProviderId();

        User user = userRepository.findById(loginId);
        if (user == null) {
            user = User.builder()
                    .id(loginId)
                    // 소셜 로그인은 비밀번호가 없으므로 임의값을 인코딩해 저장 (직접 로그인 차단용)
                    .password(passwordEncoder.encode(UUID.randomUUID().toString()))
                    .name(oAuth2UserInfo.getName())
                    .email(oAuth2UserInfo.getEmail())
                    .role("ROLE_FAMILY")
                    .useFlag("U")
                    .provider(oAuth2UserInfo.getProvider())
                    .providerId(oAuth2UserInfo.getProviderId())
                    .build();
            userRepository.save(user);
        }

        return new PrincipalDetails(user, oauth2User.getAttributes());
    }
}

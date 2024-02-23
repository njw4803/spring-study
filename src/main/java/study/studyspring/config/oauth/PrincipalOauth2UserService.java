package study.studyspring.config.oauth;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.stereotype.Service;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    /*@Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private MemberRepository memberRepository;

    // google 로그인 후처리
    // 구글로 부터 받은 userRequest 데이터에 대한 후처리되는 함수
    // 함수 종료 시 @AuthenticationPrincipal 어노테이션이 만들어진다.
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        System.out.println("getClientRegistration = " + userRequest.getClientRegistration());
        // registrationId로 어떤 OAuth로 로그인했는지 확인가능.
        System.out.println("getAccessToken = " + userRequest.getAccessToken().getTokenValue());

        OAuth2User oauth2User = super.loadUser(userRequest);
        // 구글로그인 버튼 클릭 => 구글로그인창 -> 로그인을 완료 -> code를 리턴(OAuth-Client라이브러리) -> AccessToken 요청
        // userRequest 정보 -> 회원 프로필을 받아야함(loadUser함수 호출) -> 구글로부터 회원 프로필 받음
        System.out.println("getAttributes = " + oauth2User.getAttributes());

        OAuth2UserInfo oAuth2UserInfo = null;
        //회원가입 진행
        if (userRequest.getClientRegistration().getRegistrationId().equals("google")) {
            System.out.println("구글 로그인 요청");
            oAuth2UserInfo = new GoogleUserInfo(oauth2User.getAttributes());
;       } else if (userRequest.getClientRegistration().getRegistrationId().equals("facebook")) {
            System.out.println("페이스북 로그인 요청");
            oAuth2UserInfo = new FacebookUserInfo(oauth2User.getAttributes());
        } else if (userRequest.getClientRegistration().getRegistrationId().equals("naver")) {
            System.out.println("네이버 로그인 요청");
            oAuth2UserInfo = new NaverUserInfo((Map<String, Object>) oauth2User.getAttributes().get("response"));
        } else {
            System.out.println("우리는 구글과 페이스북과 네이버만 지원해요ㅎㅎㅎ");
        }

        String provider = oAuth2UserInfo.getProvider(); // google
        String providerId = oAuth2UserInfo.getProviderId();
        String id = provider + "_" + providerId; // google_109742856182916427686
        String name = oAuth2UserInfo.getName();
        String password = bCryptPasswordEncoder.encode("겟인데어");
        String email = oAuth2UserInfo.getEmail();
        String role = "F";

       *//* Member memberEntity = memberRepository.findById(id);
        if (memberEntity == null) {
            System.out.println("소셜 로그인이 최초입니다.");
            memberEntity = memberEntity.builder()
                    .id(id)
                    .name(name)
                    .password(password)
                    .email(email)
                    .role(role)
                    .provider(provider)
                    .providerId(providerId)
                    .build();
            memberRepository.save(memberEntity);
        } else {
            System.out.println("로그인을 이미 한적이 있습니다. 당신은 자동회원가입이 되어 있습니다.");
        }*//*

        return new PrincipalDetails(memberEntity, oauth2User.getAttributes());
    }*/

}

package study.studyspring.config.oauth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import java.io.IOException;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;
import study.studyspring.config.auth.PrincipalDetails;
import study.studyspring.config.auth.jwt.JwtProperties;

/**
 * 소셜 로그인 성공 시 JWT 를 발급하고, 토큰을 쿼리스트링에 담아 프론트엔드 콜백 URL 로 리다이렉트한다.
 * (SPA 가 stateless JWT 를 쓰므로 서버 세션 대신 토큰을 프론트로 전달한다.)
 */
@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final HttpCookieOAuth2AuthorizationRequestRepository authorizationRequestRepository;

    @Value("${app.oauth2.authorized-redirect-uri}")
    private String authorizedRedirectUri;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();

        // JwtAuthenticationFilter 와 동일한 형식의 토큰을 발급한다. (id 클레임으로 JwtAuthorizationFilter 가 검증)
        String jwtToken = JWT.create()
                .withSubject("cos토큰")
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME))
                .withClaim("id", principalDetails.getUser().getId())
                .withClaim("username", principalDetails.getUser().getName())
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));

        authorizationRequestRepository.removeAuthorizationRequestCookies(request, response);

        String targetUrl = UriComponentsBuilder.fromUriString(authorizedRedirectUri)
                .queryParam("token", jwtToken)
                .build().toUriString();

        if (response.isCommitted()) {
            logger.debug("응답이 이미 커밋되어 " + targetUrl + " 로 리다이렉트할 수 없습니다.");
            return;
        }
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}

package study.studyspring.config.auth.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import java.security.Key;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import study.studyspring.config.auth.PrincipalDetails;
import study.studyspring.domain.User;
import study.studyspring.repository.UserRepository;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


// JWT토큰이 유효한지를 판단하는 필터
// 시큐리티가 filter를 가지고 있는데 그 필터 중에 BasicAuthenticationFilter라는 것이 있음.
// 권한이나 인증이 필요한 특정 주소를 요청했을 때 위 필터를 무조건 타게 되어있음.
// 만약에 권한이 인증이 필요한 주소가 아니라면 이 필터를 타지않음.
@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final UserRepository userRepository;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository) {
        super(authenticationManager);
        this.userRepository = userRepository;
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(JwtProperties.SECRET));
    }

    //인증이나 권한이 필요한 주소요청이 있을 때 해당 필터를 타게 됨.
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("인증이나 권한이 필요한 주소 요청이 됨.");
        System.out.println("request.getRequestURI() = " + request.getRequestURI());
        
        String jwtHeader = request.getHeader(JwtProperties.HEADER_STRING);
        System.out.println("jwtHeader = " + jwtHeader);

        // header가 있는지 확인
        if (jwtHeader == null || !jwtHeader.startsWith("Bearer")) {
            chain.doFilter(request,response);
            return;
        }

        //JWT 토큰을 검증을 해서 정상적인 사용자인지 확인
        String jwtToken = request.getHeader(JwtProperties.HEADER_STRING).replace(JwtProperties.TOKEN_PREFIX,"");
        // validateToken(jwtToken);

        System.out.println("jwtToken = " + jwtToken);
        String id = "";
        try {
            id = JWT.require(Algorithm.HMAC512("cos")).build().verify(jwtToken).getClaim("id").asString();
        } catch (JWTDecodeException e) {
            throw new JWTDecodeException("JWT DecodeException");
        } catch (IllegalArgumentException e) {
            logger.error("an error occured during getting username from token", e);
            throw new IllegalArgumentException("유효하지 않은 토큰");
        } catch (TokenExpiredException e) {
            logger.warn("the token is expired and not valid anymore", e);
            ((HttpServletResponse) response).sendError(401, "토큰 기한 만료");
        } catch(SignatureException e){
            logger.error("Authentication Failed. Username or Password not valid.");
            throw new SignatureException("사용자 인증 실패");
        } catch (Exception e) {
            e.printStackTrace();
            chain.doFilter(request,response);
            return;
        }

        System.out.println("id = " + id);
        //서명이 정상적으로 됨.
        if (id != null) {
            User userEntity = userRepository.findById(id);
            System.out.println("userEntity = " + userEntity);
            //Jwt 토큰 서명을 통해서 서명이 정상이면 Authentication 객체를 만들어준다.
            PrincipalDetails principalDetails = new PrincipalDetails(userEntity);
            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(principalDetails,null, principalDetails.getAuthorities());
            System.out.println("authentication = " + authentication);
            // 강제로 시큐리티의 세션에 접근하여 Authentication 객체를 저장
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request,response);
    }

    /**
     * Access 토큰을 검증
     */
    public boolean validateToken(String token){
        return true;
    }
}

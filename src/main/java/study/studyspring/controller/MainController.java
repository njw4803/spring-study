package study.studyspring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import study.studyspring.config.PasswordEncoderConfig;
import study.studyspring.config.auth.PrincipalDetails;
import study.studyspring.domain.Member;
import study.studyspring.repository.MemberRepository;

@Controller
public class MainController {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String main() {
        return "index";
    }

    @GetMapping("/member")
    public @ResponseBody String member(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        System.out.println("principalDetails.getMember() = " + principalDetails.getMember());
        return "member";
    }

    @GetMapping("/premium")
    public @ResponseBody String admin() {
        return "premium";
    }

    @GetMapping("/vip")
    public @ResponseBody String manager() {
        return "vip";
    }

    //spring security에서 낚아챔 - SecurityConfig 파일 생성 후 작동 안함
    @GetMapping("/loginForm")
    public String loginForm() {
        return "loginForm";
    }

    @PostMapping("/join")
    public String join(Member member) {
        System.out.println("member = " + member);
        member.setRole("ADMIN");
        member.setGrade("F");
        member.setUseFlag("U");
        String rawPassword = member.getPassword();
        String encPassword = passwordEncoder.encode(rawPassword);
        member.setPassword(encPassword);
        memberRepository.save(member); //회원가입

        return "redirect:/loginForm";
    }

    @Secured("ROLE_ADMIN") // ROLE_ADMIN 권한을 가지고있어야만 접근 가능
    @GetMapping("/info")
    public @ResponseBody String info() {
        return "개인정보";
    }
}

package study.studyspring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import study.studyspring.config.auth.PrincipalDetails;
import study.studyspring.domain.User;
import study.studyspring.repository.UserRepository;
import study.studyspring.service.UserService;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ApiController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/user")
    public @ResponseBody String user(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        System.out.println("principalDetails.getUser() = " + principalDetails.getUser());
        return "user";
    }

    @GetMapping("/premium")
    public @ResponseBody String admin() {
        return "premium";
    }

    @GetMapping("/vip")
    public @ResponseBody String manager() {
        return "vip";
    }

    @PostMapping("/signUp")
    public int signUp(@RequestBody User user) {

        System.out.println("user = " + user);
        user.setRole("ROLE_FAMILY");
        user.setUseFlag("U");
        String rawPassword = user.getPassword();
        String encPassword = passwordEncoder.encode(rawPassword);
        user.setPassword(encPassword);
        userRepository.save(user);//회원가입
        if (user == null) return 0;

        return 1;
    }
    
    @PostMapping("/join")
    public String userJoin(@RequestBody User user) {
        String result = "회원가입실패";
        try {
            result = userService.join(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}

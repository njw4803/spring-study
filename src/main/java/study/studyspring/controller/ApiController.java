package study.studyspring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import study.studyspring.service.MemberService;

@RestController
@RequestMapping("/api/v1")
public class ApiController {

    private final MemberService memberService;

    @Autowired // 연결시켜주는 역할, 스프링 빈에 등록되어있는 객체를 주입시킨다. 생성자가 하나면 생략 가능
    public ApiController(MemberService memberService) {
        this.memberService = memberService;
        System.out.println("memberService = " + memberService.getClass());
    }
}

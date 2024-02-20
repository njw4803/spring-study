package study.studyspring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class RestApiController {

    @GetMapping("/home")
    public String home()  {
        return "<h1>home</h1>";
    }

    @PostMapping("/token")
    public String token()  {
        return "<h1>token</h1>";
    }


}

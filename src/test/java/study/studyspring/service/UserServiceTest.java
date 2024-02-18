package study.studyspring.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import study.studyspring.domain.User;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@RequiredArgsConstructor
@Commit
class UserServiceTest {

    private final UserService userService;

    @Test
    void join() {
        //Given 상황
        User user = User.builder()
                .id("hello4474")
                .name("hello4474")
                .password("1234")
                .phone("01090231227")
                .role("V")
                .useFlag("Y")
                .build();

        //When 검증
        String result = userService.join(user);

        //Then 결과
        assertThat(result).isEqualTo("회원가입완료");
    }
}
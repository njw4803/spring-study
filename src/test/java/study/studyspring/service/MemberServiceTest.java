package study.studyspring.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import study.studyspring.domain.Member;
import study.studyspring.repository.MemberRepository;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Commit
class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
    @Test
    void join() {
        //Given 상황
        Member member = new Member();
        member.setId("hello");
        member.setName("hello");
        member.setAddr("test");
        member.setDetailAddr("test");
        member.setRole("V");
        member.setPhone("01090231227");
        member.setCreateDate(LocalDateTime.now());
        member.setUseFlag("Y");

        //When 검증
        Long saveId = memberService.join(member);
        //Then 결과
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }
}
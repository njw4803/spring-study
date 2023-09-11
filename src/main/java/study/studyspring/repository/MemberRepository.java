package study.studyspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.studyspring.domain.Member;

import java.util.Optional;

public interface MemberRepository{

    Member save(Member member);
    Optional<Member> findById(Long id);
    Optional<Member> findByName(String name);

}

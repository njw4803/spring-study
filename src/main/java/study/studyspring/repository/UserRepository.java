package study.studyspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.studyspring.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    User save(User member);

    User findById(String id);

}

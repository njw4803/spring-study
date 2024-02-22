package study.studyspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.studyspring.domain.Notice;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

}
package study.studyspring.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "TB_MEMBER")
public class Member {

    @Id @Column(name = "idx") @GeneratedValue(strategy = GenerationType.IDENTITY) // IDENTITY DB가 자동으로 생성해줌
    private Long idx;
    private String id;
    private String name;
    private String phone;
    private String addr;
    private String detailAddr;
    private String grade;
    private String useFlag;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

}

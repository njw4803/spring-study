package study.studyspring.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "MEMBER")
//@DynamicInsert (insert 시 null 인필드 제외)  - DB에 default가 설정되어있을 시 사용
/*
@DynamicUpdate (update 시 null 인필드 제외) - 참고:https://huisam.tistory.com/entry/jpa-query-statement
단점 - DynamicUpdate 어노테이션은 변경된 값을 감지하여 매번 query statement 문을 다시 생성하기 때문에 비용이 증가
        그래서 application 성능에 안좋은 영향을 미칠수가 있다는 단점 또한 존재
DynamicUpdate 는 OptimisticLocking 을 사용할 때만 고려
*/
public class Member extends BaseEntity {

    @Id
    @Column(name = "idx") @GeneratedValue(strategy = GenerationType.IDENTITY) // IDENTITY DB가 자동으로 생성해줌
    private Long idx;
    private String id;
    private String password;
    private String name;
    private String phone;
    private String email;
    private String addr;
    private String detailAddr;
    private String role;
    private String useFlag;
    private String provider;
    private String providerId;

    @Builder
    public Member(String id, String password, String name,
                  String phone, String email, String addr,
                  String detailAddr, String role, String useFlag,
                  String provider, String providerId) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.addr = addr;
        this.detailAddr = detailAddr;
        this.role = role;
        this.useFlag = useFlag;
        this.provider = provider;
        this.providerId = providerId;
    }
}

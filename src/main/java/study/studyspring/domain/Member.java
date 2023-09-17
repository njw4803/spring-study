package study.studyspring.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "MEMBER")
//@DynamicInsert (insert 시 null 인필드 제외)  - DB에 default가 설정되어있을 시 사용
/*
@DynamicUpdate (update 시 null 인필드 제외) - 참고:https://huisam.tistory.com/entry/jpa-query-statement
단점 - DynamicUpdate 어노테이션은 변경된 값을 감지하여 매번 query statement 문을 다시 생성하기 때문에 비용이 증가
        그래서 application 성능에 안좋은 영향을 미칠수가 있다는 단점 또한 존재
DynamicUpdate 는 OptimisticLocking 을 사용할 때만 고려
*/
public class Member {

    @Id
    @Column(name = "idx") @GeneratedValue(strategy = GenerationType.IDENTITY) // IDENTITY DB가 자동으로 생성해줌
    private Long idx;
    private String id;
    private String password;
    private String name;
    private String phone;
    private String addr;
    private String detailAddr;
    private String role;
    private String useFlag;
    /*
    Response(서버→클라이언트) : @JsonFormat 사용.
    Request(클라이언트→서버) : @DateTimeFormat 사용.
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.sssZ")
    */

    @CreationTimestamp // Insert 쿼리가 발생할 때 현재 시간 값을 적용.
    private LocalDateTime createDate;

    //@UpdateTimestamp Update 쿼리가 발생했을 때 현재 시간 값을 적용.
    private LocalDateTime updateDate;

}

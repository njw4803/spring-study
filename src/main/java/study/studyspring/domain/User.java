package study.studyspring.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // default는 public
@Table(name = "USER")
//@DynamicInsert (insert 시 null 인필드 제외)  - DB에 default가 설정되어있을 시 사용
/*
@DynamicUpdate (update 시 null 인필드 제외) - 참고:https://huisam.tistory.com/entry/jpa-query-statement
단점 - DynamicUpdate 어노테이션은 변경된 값을 감지하여 매번 query statement 문을 다시 생성하기 때문에 비용이 증가
        그래서 application 성능에 안좋은 영향을 미칠수가 있다는 단점 또한 존재
DynamicUpdate 는 OptimisticLocking 을 사용할 때만 고려
*/
public class User extends BaseEntity {

    @Id
    @Column(name = "idx")
    @GeneratedValue(strategy = GenerationType.IDENTITY) // IDENTITY DB가 자동으로 생성해줌
    private Long idx;
    private String id;
    private String password;
    private String name;
    private String phone;
    private String email;
    //private Integer zipCode;
    //private String addr;
    //private String detailAddr;
    private String role;
    private String useFlag;
    private String provider;
    private String providerId;

    public void setId(String id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setUseFlag(String useFlag) {
        this.useFlag = useFlag;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    @Builder
    public User(String id, String password, String name,
                String phone, String email, String role, String useFlag,
                String provider, String providerId) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.role = role;
        this.useFlag = useFlag;
        //this.provider = provider;
        //this.providerId = providerId;
    }
}

package study.studyspring.domain;

import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
public abstract class BaseEntity {

    /*
    Response(서버→클라이언트) : @JsonFormat 사용.
    Request(클라이언트→서버) : @DateTimeFormat 사용.
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.sssZ")
    */

    @CreationTimestamp // Insert 쿼리가 발생할 때 현재 시간 값을 적용.
    private LocalDateTime createDate;

    //@UpdateTimestamp Update 쿼리가 발생했을 때 현재 시간 값을 적용.
    private LocalDateTime updateDate;

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

}

package study.studyspring.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 노트 등록 Dto
 */
@Getter
@Builder
@ToString
public class NoteRegisterDto {

    private String title;
    private String content;

}
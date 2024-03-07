package study.studyspring.dto;

/**
 * @param code 1 성공, -1 실패
 * @param msg
 * @param data
 */
public record ExceptionResponseDTO<T>(Integer code, String msg, T data) {

}

package study.studyspring.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import study.studyspring.dto.ExceptionResponseDTO;
import study.studyspring.dto.Error;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RestControllerAdvice
public class CustomExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(Exception.class) // Exception 터지면 작동
    public ResponseEntity<?> apiException(Exception e) {
        e.printStackTrace();
        return new ResponseEntity<>(new ExceptionResponseDTO<>(-1,e.getMessage(),null), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateKeyException .class) // DuplicateKeyException 터지면 작동
    public ResponseEntity<?> apiException(DuplicateKeyException e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(new ExceptionResponseDTO<>(-1,e.getMessage(),null), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class) // MethodArgumentNotValidException 터지면 작동
    public ResponseEntity<?> validationApiException(MethodArgumentNotValidException e) {
        log.error(e.getMessage());
        Map<String, String> errors = e.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        fieldError -> Optional.of(Error.ErrorMessage.getErrorMessage(Objects.requireNonNull(fieldError))).orElse("")
                ));
        return new ResponseEntity<>(new ExceptionResponseDTO<>(-1,"유효성 검사 실패",errors), HttpStatus.BAD_REQUEST);
    }
}

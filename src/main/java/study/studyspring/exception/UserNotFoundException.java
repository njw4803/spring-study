package study.studyspring.exception;

/**
 * 유저를 찾을 수 없을 때 발생하는 Exception
 */
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String message) {
        super(message);
    }


}

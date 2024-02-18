package study.studyspring.service;

import study.studyspring.domain.User;
import study.studyspring.repository.UserRepository;

import java.util.Optional;

public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 회원가입
     */
    public String join(User user) {
        validateDuplicateMember(user); //중복 회원 검증
        User userEntity = userRepository.save(user);
        String result = "회원가입 완료";
        if (userEntity == null) {
            result = "회원가입 실패";
        }
        return result;
    }

    private void validateDuplicateMember(User user) {
        Optional<User> userEntity = Optional.ofNullable(userRepository.findById(user.getId()));
        userEntity.ifPresent(m -> { // Null이 아니라 값이 있으면 동작
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    /**
     * 회원 조회
     */
    public Optional<User> findByUserId(Long memberId) {
        return userRepository.findById(memberId);
    }
}

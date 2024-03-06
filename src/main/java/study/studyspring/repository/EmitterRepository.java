package study.studyspring.repository;

import java.util.Map;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface EmitterRepository {

    SseEmitter save(String id, SseEmitter sseEmitter);
    void saveEventCache(String id, Object event);
    Map<String, SseEmitter> findAllStartById(String id);
    void deleteAllStartByWithId(String id);

}

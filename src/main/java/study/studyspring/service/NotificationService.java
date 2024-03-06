package study.studyspring.service;

import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import study.studyspring.repository.NotificationType;

public interface NotificationService {

    SseEmitter connection(String lastEventId, HttpServletResponse response);
    void sendToClient(SseEmitter emitter, String id, Object data);
    SseEmitter send(String title, String content, NotificationType notificationType);
}

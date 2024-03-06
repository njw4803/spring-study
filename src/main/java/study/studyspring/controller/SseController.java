package study.studyspring.controller;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import study.studyspring.repository.NotificationType;
import study.studyspring.service.NotificationService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class SseController {

    private final NotificationService notificationService;

    @GetMapping(value = "/connect", produces = "text/event-stream")
    public ResponseEntity<SseEmitter> sseConnection(@RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId, HttpServletResponse response) {

        return new ResponseEntity<>(notificationService.connection(lastEventId, response), HttpStatus.OK);
    }

    @GetMapping(value = "/send", produces = "text/event-stream")
    public ResponseEntity<SseEmitter> sseSend(@RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId, HttpServletResponse response) {
        NotificationType notificationType = new NotificationType();
        return new ResponseEntity<>(notificationService.send("title","content",notificationType), HttpStatus.OK);
    }

}

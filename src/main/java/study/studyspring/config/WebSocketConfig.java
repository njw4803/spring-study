package study.studyspring.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import study.studyspring.handler.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {

    private final WebSocketHandler webSocketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketHandler, "/socketServer").setAllowedOrigins("*");
                // .withSockJS(); // Enableing SockJS
    }
}

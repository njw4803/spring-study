package study.studyspring.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage {
    /*
    private String messageType; // 메시지 타입
    private String channelId; // 방 번호
    private String senderId; // 채팅을 보낸 사람
    private String message; // 메시지
    */

    private MessageType type;
    private String content;
    private String sender;
    private String channelId; // 방 번호

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}
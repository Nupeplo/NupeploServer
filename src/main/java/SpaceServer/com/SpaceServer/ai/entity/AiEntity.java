package SpaceServer.com.SpaceServer.ai.entity;

import org.springframework.data.annotation.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
@Getter @Setter
@Document(collection = "ai_messages")
public class AiEntity {
    @Id
    private String id;
    private String userId; // 채팅자
    private String role;
    private String message; // 메세지
    private long timestamp; // 보내는 시간

    public AiEntity(String userId, String role,String message) {
        this.userId = userId;
        this.role = role;
        this.message = message;
        this.timestamp = System.currentTimeMillis();
    }
}

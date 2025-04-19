package SpaceServer.com.SpaceServer.ai.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageDto {
    private String role;     // "user", "assistant", "system"
    private String content;
}

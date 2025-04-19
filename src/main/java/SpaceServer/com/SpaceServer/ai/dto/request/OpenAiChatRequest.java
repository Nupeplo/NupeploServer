package SpaceServer.com.SpaceServer.ai.dto.request;

import SpaceServer.com.SpaceServer.ai.dto.ChatMessageDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class OpenAiChatRequest {
    private String model;
    private List<ChatMessageDto> messages;
}

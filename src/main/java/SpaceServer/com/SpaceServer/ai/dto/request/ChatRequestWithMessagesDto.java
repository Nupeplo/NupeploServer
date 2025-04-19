package SpaceServer.com.SpaceServer.ai.dto.request;

import SpaceServer.com.SpaceServer.ai.dto.ChatMessageDto;
import lombok.Getter;

import java.util.List;

@Getter
public class ChatRequestWithMessagesDto {
    private List<ChatMessageDto> messages;
}

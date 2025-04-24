package SpaceServer.com.SpaceServer.ai;


import SpaceServer.com.SpaceServer.ai.dto.request.ChatRequestWithMessagesDto;
import SpaceServer.com.SpaceServer.ai.dto.response.ChatResponseDto;
import SpaceServer.com.SpaceServer.ai.service.OpenAiService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RequestMapping("/api/v1/chat")
@RestController
@AllArgsConstructor
public class AiController {
    private final OpenAiService openAiService;

    @Operation(summary = "AI 대화형 채팅", description = "프론트에서 보낸 메시지 리스트 기반으로 GPT에게 응답 받기")
    @PostMapping("/ai")
    public Mono<ResponseEntity<ChatResponseDto>> chat(@RequestBody ChatRequestWithMessagesDto request) {
        return openAiService.chatWithMessages(request.getMessages())
                .map(response -> ResponseEntity.ok(new ChatResponseDto(response)));
    }
}

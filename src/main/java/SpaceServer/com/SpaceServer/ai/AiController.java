package SpaceServer.com.SpaceServer.ai;


import SpaceServer.com.SpaceServer.ai.entity.AiEntity;
import SpaceServer.com.SpaceServer.ai.service.AiService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping("/api/v1/chat")
@RestController
@AllArgsConstructor
public class AiController {
    private final AiService aiService;

    @Operation(summary = "AI 대화형 채팅", description = "프론트에서 보낸 메시지 리스트 기반으로 GPT에게 응답 받기")
    @PostMapping("/ask")
    public String ask(@RequestParam String userId, @RequestParam String message) {
        return aiService.chat(userId, message);
    }

    @GetMapping("/history")
    public List<AiEntity> getHistory(@RequestParam String userId) {
        return aiService.getChatHistory(userId);
    }
}

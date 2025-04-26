package SpaceServer.com.SpaceServer.ai.service;

import SpaceServer.com.SpaceServer.ai.entity.AiEntity;
import SpaceServer.com.SpaceServer.ai.repository.AiRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AiService {
    private final AiRepository aiRepository;
    private final OpenAiChatClient openAiChatClient;

    public String chat(String userId, String userMessage) {
        // 1. 사용자의 질문 저장
        AiEntity userChat = new AiEntity(userId, "USER", userMessage);
        aiRepository.save(userChat);

        // 2. AI에게 질문 + 시스템 메시지
        String prompt = """
            당신은 SpaceServerAI입니다.
            우주에 대해 쉽고 친절하게 답변해주세요!
            한 줄로 짧게 답변해주세요.

            질문: %s
            답변:""".formatted(userMessage);

        String aiResponse = openAiChatClient.call(prompt);

        // 3. AI의 응답 저장
        AiEntity aiChat = new AiEntity(userId, "ASSISTANT", aiResponse);
        AiEntity savedAi = aiRepository.save(aiChat);
        log.info("Saved AI message: {}", savedAi.getId());


        return aiResponse;
    }

    public List<AiEntity> getChatHistory(String userId) {
        return aiRepository.findByUserIdOrderByTimestampAsc(userId);
    }
}

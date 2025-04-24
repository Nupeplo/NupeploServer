package SpaceServer.com.SpaceServer.ai.service;

import SpaceServer.com.SpaceServer.ai.dto.ChatMessageDto;
import SpaceServer.com.SpaceServer.ai.dto.request.OpenAiChatRequest;
import SpaceServer.com.SpaceServer.ai.dto.response.OpenAiChatResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OpenAiService {

    private final WebClient openAiWebClient;

    public Mono<String> chatWithMessages(List<ChatMessageDto> messages) {
        // 요청 객체 생성
        OpenAiChatRequest openAiRequest = new OpenAiChatRequest("gpt-3.5-turbo", messages);

        return openAiWebClient.post()
                .bodyValue(openAiRequest)
                .retrieve()
                .bodyToMono(OpenAiChatResponse.class)
                .map(res -> res.getChoices().get(0).getMessage().getContent());
    }
}

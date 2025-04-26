package SpaceServer.com.SpaceServer.ai.config;

import org.springframework.ai.chat.*;

import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class Config {
    @Bean
    public OpenAiApi openAiApi(@Value("${AI}") String apiKey) {
        return new OpenAiApi(apiKey);
    }

    @Bean
    public OpenAiChatClient openAiChatClient(OpenAiApi openAiApi) {
        return new OpenAiChatClient(openAiApi);
    }
}

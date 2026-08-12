package com.gunitha.springai.config.rag;

import com.gunitha.springai.advisor.TokenUsageAuditAdvisor;
import com.gunitha.springai.rag.sir.WebSearchDocumentRetriever;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.rag.advisor.RetrievalAugmentationAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

import java.util.List;

@Configuration
public class WebSearchRagChatClientConfig {

    @Bean("webSearchRagChatClient")
    public ChatClient webSearchRagChatClient(OpenAiChatModel openAiChatModel, ChatMemory chatMemory
            , RestClient.Builder restClientBuilder) {
        return ChatClient.builder(openAiChatModel)
                .defaultAdvisors(List.of(new SimpleLoggerAdvisor(), MessageChatMemoryAdvisor.builder(chatMemory).build(), new TokenUsageAuditAdvisor()))
                .defaultAdvisors(RetrievalAugmentationAdvisor.builder()
                        .documentRetriever(WebSearchDocumentRetriever.builder()
                                .restClientBuilder(restClientBuilder).maxResults(5)
                                .build())
                        .build())
                .build();
    }
}

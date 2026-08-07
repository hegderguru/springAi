package com.gunitha.springai.config.rag;

import com.gunitha.springai.advisor.TokenUsageAuditAdvisor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.rag.advisor.RetrievalAugmentationAdvisor;
import org.springframework.ai.rag.retrieval.search.VectorStoreDocumentRetriever;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RagChatClientConfig {

    @Autowired
    VectorStore vectorStore;

    @Bean
    public ChatClient openAiRagChatClient(OpenAiChatModel openAiChatModel, ChatMemory chatMemory) {
        Advisor advisor = new TokenUsageAuditAdvisor();
        return ChatClient.builder(openAiChatModel)
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
                .defaultAdvisors(SimpleLoggerAdvisor.builder().build(), advisor)
                .defaultOptions(OpenAiChatOptions.builder()
                        .temperature(1.0))
                .build();
    }

    @Bean
    public ChatClient openAiRagAdvisorChatClient(OpenAiChatModel openAiChatModel, ChatMemory chatMemory,RetrievalAugmentationAdvisor retrievalAugmentationAdvisor) {
        Advisor advisor = new TokenUsageAuditAdvisor();
        return ChatClient.builder(openAiChatModel)
                .defaultAdvisors(SimpleLoggerAdvisor.builder().build(), advisor,retrievalAugmentationAdvisor)
                .defaultOptions(OpenAiChatOptions.builder()
                        .temperature(1.0))
                .build();
    }

    @Bean
    public RetrievalAugmentationAdvisor retrievalAugmentationAdvisor(ChatClient chatClient) {
        return RetrievalAugmentationAdvisor.builder()
                .documentRetriever(VectorStoreDocumentRetriever.builder().vectorStore(vectorStore)
                        .topK(3).similarityThreshold(0.5).build()).build();
    }

}

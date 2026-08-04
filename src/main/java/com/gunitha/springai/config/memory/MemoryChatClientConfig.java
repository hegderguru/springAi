package com.gunitha.springai.config.memory;

import org.jspecify.annotations.NonNull;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.memory.repository.jdbc.JdbcChatMemoryRepository;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class MemoryChatClientConfig {

    @Bean
    public ChatClient grokMemoryChatClient(OpenAiChatModel openAiChatModel, ChatMemory chatMemory) {
        ChatMemory cleanChatMemory = createCleanChatMemory(chatMemory);
        return ChatClient.builder(openAiChatModel)
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
                .defaultAdvisors(SimpleLoggerAdvisor.builder().build())
                .defaultOptions(OpenAiChatOptions.builder()
                        .temperature(0.8))
                .build();
    }

    @Bean
    public ChatClient grokJdbcMemoryChatClient(OpenAiChatModel openAiChatModel, JdbcChatMemoryRepository jdbcChatMemoryRepository) {
        MessageWindowChatMemory.Builder builder = MessageWindowChatMemory.builder()
                .maxMessages(50)
                .chatMemoryRepository(jdbcChatMemoryRepository);
        return ChatClient.builder(openAiChatModel)
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(builder.build()).build())
                .defaultAdvisors(SimpleLoggerAdvisor.builder().build())
                .defaultOptions(OpenAiChatOptions.builder()
                        .temperature(0.8))
                .build();
    }

    private static @NonNull ChatMemory createCleanChatMemory(ChatMemory chatMemory) {
        return new ChatMemory() {

            @Override
            public void clear(String conversationId) {
                chatMemory.clear(conversationId);
            }

            @Override
            public List<Message> get(String conversationId) {
                return getMessages(conversationId, 20);
            }

            public List<Message> getMessages(String conversationId, int lastN) {
                return chatMemory.get(conversationId).stream().map(msg ->
                        (msg instanceof AssistantMessage am) ? new AssistantMessage(am.getText()) : msg
                ).toList();
            }

            @Override
            public void add(String conversationId, List<Message> messages) {
                addMessages(conversationId, messages);
            }

            public void addMessages(String conversationId, List<Message> messages) {
                var sanitized = messages.stream().map(msg ->
                        (msg instanceof AssistantMessage am) ? new AssistantMessage(am.getText()) : msg
                ).toList();
                chatMemory.add(conversationId, sanitized);
            }
        };
    }


}

package com.gunitha.springai.config.tool;

import com.gunitha.springai.advisor.TokenUsageAuditAdvisor;
import com.gunitha.springai.tool.TimeTool;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TimeChatClientConfig {

    @Autowired
    TimeTool timeTool;

    @Bean
    public ChatClient timeChatClient(OpenAiChatModel openAiChatModel, ChatMemory chatMemory) {
        return ChatClient.builder(openAiChatModel)
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
                .defaultAdvisors(SimpleLoggerAdvisor.builder().build())
                .defaultAdvisors(new TokenUsageAuditAdvisor())
                .defaultOptions(OpenAiChatOptions.builder().temperature(0.8))
                .defaultTools(timeTool)
                .build();
    }
}

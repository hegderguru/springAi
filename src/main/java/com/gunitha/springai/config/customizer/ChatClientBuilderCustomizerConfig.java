package com.gunitha.springai.config.customizer;

import com.gunitha.springai.advisor.TokenUsageAuditAdvisor;
import com.gunitha.springai.tool.TimeTool;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.ChatClientBuilderCustomizer;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatClientBuilderCustomizerConfig {

    @Bean
    public ChatClient.Builder openAiChatClientBuilder(OpenAiChatModel  openAiChatModel) {
        return ChatClient.builder(openAiChatModel);
    }

    @Bean
    public ChatClientBuilderCustomizer loggerAdvisor() {
        return openAiChatClientBuilder -> openAiChatClientBuilder.defaultAdvisors(new SimpleLoggerAdvisor());
    }

    @Bean
    @ConditionalOnProperty(name = "audit.token-usage.enabled",havingValue = "true")
    public ChatClientBuilderCustomizer auditAdvisor() {
        return openAiChatClientBuilder -> openAiChatClientBuilder.defaultAdvisors(new TokenUsageAuditAdvisor());
    }

    @Bean
    public ChatClientBuilderCustomizer defaultTools() {
        return openAiChatClientBuilder -> openAiChatClientBuilder.defaultTools(new TimeTool());
    }

}

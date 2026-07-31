package com.gunitha.springai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.template.st.StTemplateRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("prompt-stuffing")
public class PromptStuffingController {

    @Autowired
    ChatClient chatClient;

    @Value("classpath:/promptTemplate/spring-versions-template.st")
    Resource springVersionPromptTemplate;

    static PromptTemplate promptTemplate = null;

    static {
        promptTemplate = PromptTemplate.builder()
                .renderer(StTemplateRenderer.builder()
                        .startDelimiterToken('<')
                        .endDelimiterToken('>')
                        .build())
                .template("Hello <message>")
                .build();
    }

    @GetMapping("chat")
    public String promptStuffing(String message) {
        return chatClient.prompt()
                .system(springVersionPromptTemplate)
                .user(promptTemplate.render(Map.of("message", message)))
                .call().content();

    }
}

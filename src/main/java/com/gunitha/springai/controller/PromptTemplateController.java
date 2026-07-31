package com.gunitha.springai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("prompt-template")
public class PromptTemplateController {

    @Autowired
    ChatClient chatClient;

    @Value("classpath:/promptTemplate/movie-prompt-template.st")
    String moviePromptTemplate;

    String realEstatePromptTemplate = """
                A customer with name {customerName} has asked for {customerMessage}.
                You are going to respond user over email (email like response) with available opportunities.
                You are answering questions related to real estate.
            """;

    @GetMapping("realty/email")
    public String realtyEmailResponse(@RequestParam String customerName, @RequestParam String customerMessage) {
        return chatClient.prompt()
                .system("You are a realty Customer, Interested in buying plot or flat. You will email the customer with available opportunities in real estate.")
                .user(promptUserSpec -> promptUserSpec.text(realEstatePromptTemplate)
                        .param("customerName", customerName)
                        .param("customerMessage", customerMessage))
                .call().content();
    }

    @GetMapping("movie/email")
    public String movieEmailResponse(@RequestParam String customerName, @RequestParam String customerMessage) {
        return chatClient.prompt()
                .system("You are a cinema enthusiast, Interested in watching movies. You will email the customer with available best in movies.")
                .user(promptUserSpec -> promptUserSpec.text(moviePromptTemplate)
                        .param("customerName", customerName)
                        .param("customerMessage", customerMessage))
                .call().content();
    }
}

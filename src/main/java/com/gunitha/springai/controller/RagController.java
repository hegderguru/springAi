package com.gunitha.springai.controller;


import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/rag")
public class RagController {

    @Qualifier("ollamaMemoryChatClient")
    @Autowired
    private ChatClient ollamaMemoryChatClient;

    @Qualifier("openAiRagChatClient")
    @Autowired
    private ChatClient openAiRagChatClient;

    @Autowired
    private VectorStore vectorStore;

    @Value("classpath:promptTemplate/systemPromptGovtSchemesTemplate.st")
    Resource systemPromptGovtSchemesTemplate;

    @GetMapping("chat")
    public Mono<String> chat(@RequestParam("username") String username, @RequestParam String message) {
        /*SearchRequest searchRequest = SearchRequest.builder().query(message).topK(3)
                .similarityThreshold(0.5)
                .build();
        List<Document> documents = vectorStore.similaritySearch(searchRequest);
        String collect = documents.stream().map(Document::getText)
                .collect(Collectors.joining(System.lineSeparator()));
        String answer = chatClient.prompt()
                .system(promptSystemSpec -> promptSystemSpec.text(systemPromptGovtSchemesTemplate)
                        .param("documents", collect))
                .advisors(advisorSpec -> advisorSpec.param(ChatMemory.CONVERSATION_ID, username))
                .user(message)
                .call()
                .content();
        return ResponseEntity.ok(answer);*/

        return Mono.fromCallable(() -> {
            SearchRequest searchRequest = SearchRequest.builder().query(message).topK(3)
                    .similarityThreshold(0.5)
                    .build();
            List<Document> documents = vectorStore.similaritySearch(searchRequest);
            String collect = documents.stream().map(Document::getText)
                    .collect(Collectors.joining(System.lineSeparator()));
            return ollamaMemoryChatClient.prompt()
                    .system(promptSystemSpec -> promptSystemSpec.text(systemPromptGovtSchemesTemplate)
                            .param("documents", collect))
                    .advisors(advisorSpec -> advisorSpec.param(ChatMemory.CONVERSATION_ID, username))
                    .user(message)
                    .call()
                    .content();
        }).subscribeOn(Schedulers.boundedElastic());
    }

    @GetMapping("openAiChat")
    public Mono<String> openAiChat(@RequestParam("username") String username, @RequestParam String message) {
        /*SearchRequest searchRequest = SearchRequest.builder().query(message).topK(3)
                .similarityThreshold(0.5)
                .build();
        List<Document> documents = vectorStore.similaritySearch(searchRequest);
        String collect = documents.stream().map(Document::getText)
                .collect(Collectors.joining(System.lineSeparator()));
        String answer = openAiRagChatClient.prompt()
                .system(promptSystemSpec -> promptSystemSpec.text(systemPromptGovtSchemesTemplate)
                        .param("documents", collect))
                .advisors(advisorSpec -> advisorSpec.param(ChatMemory.CONVERSATION_ID, username))
                .user(message)
                .call()
                .content();
        return ResponseEntity.ok(answer);*/

        return Mono.fromCallable(() -> {
            SearchRequest searchRequest = SearchRequest.builder().query(message).topK(3)
                    .similarityThreshold(0.5)
                    .build();
            List<Document> documents = vectorStore.similaritySearch(searchRequest);
            String collect = documents.stream().map(Document::getText)
                    .collect(Collectors.joining(System.lineSeparator()));
            return openAiRagChatClient.prompt()
                    .system(promptSystemSpec -> promptSystemSpec.text(systemPromptGovtSchemesTemplate)
                            .param("documents", collect))
                    .advisors(advisorSpec -> advisorSpec.param(ChatMemory.CONVERSATION_ID, username))
                    .user(message)
                    .call()
                    .content();
        }).subscribeOn(Schedulers.boundedElastic());
    }

}

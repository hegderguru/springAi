package com.gunitha.grok.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@RestController
@RequestMapping("api/ollama/v1")
public class OllamaChatController {

    @Autowired
    ChatClient ollamaChatClient;

    @GetMapping("chat")
    public Mono<String> chat(@RequestParam String message) {
        return Mono.fromCallable(() -> ollamaChatClient.prompt(message)
                        .system("You are AI assistant. You answer questions related to Spring boot Java implementation")
                        .call().content())
                .subscribeOn(Schedulers.boundedElastic()); // <-- Forces the blocking call onto a safe thread
    }

    @GetMapping("chat2")
    public Mono<String> chatStream(@RequestParam String message) {
        // Returns a truly non-blocking reactive stream of responses
        return ollamaChatClient.prompt(message).stream().content().collectList()
                .flatMap(strings -> Mono.just(String.join(" ",strings)));
    }

}

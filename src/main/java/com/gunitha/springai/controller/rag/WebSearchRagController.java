package com.gunitha.springai.controller.rag;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@RestController
@RequestMapping("/api/ragWebsearch")
public class WebSearchRagController {

    @Qualifier("webSearchRagChatClient")
    @Autowired
    ChatClient webSearchRagChatClient;

    @GetMapping("tavily")
    public Mono<String> tavily(@RequestParam("username") String username, @RequestParam String message) {
        return Mono.fromCallable(() -> webSearchRagChatClient.prompt()
                .advisors(advisorSpec -> advisorSpec.param(ChatMemory.CONVERSATION_ID, username))
                .user(message)
                .call()
                .content()).subscribeOn(Schedulers.boundedElastic());
    }
}

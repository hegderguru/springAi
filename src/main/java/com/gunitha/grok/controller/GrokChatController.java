package com.gunitha.grok.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/grok/v1")
public class GrokChatController {

    @Autowired
    ChatClient grokChatClient;

    @GetMapping("chat")
    public String chat(@RequestParam String message) {
        return grokChatClient.prompt(message)
                .system("You are AI assistant. You answer questions related to Spring boot Java implementation")
                .call().content();
    }
}

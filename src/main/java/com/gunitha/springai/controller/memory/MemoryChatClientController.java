package com.gunitha.springai.controller.memory;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/memory/v1")
public class MemoryChatClientController {

    @Qualifier("grokMemoryChatClient")
    @Autowired
    ChatClient grokMemoryChatClient;

    @GetMapping("chat")
    public ResponseEntity<String> memory(String message) {
        return ResponseEntity.ok(grokMemoryChatClient
                .prompt()
                .user(message)
                .advisors(advisorSpec -> advisorSpec
                        .param(ChatMemory.CONVERSATION_ID, "default"))
                .call()
                .content());
    }
}
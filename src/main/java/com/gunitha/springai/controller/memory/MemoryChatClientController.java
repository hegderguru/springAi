package com.gunitha.springai.controller.memory;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/memory/v1")
public class MemoryChatClientController {

    @Qualifier("grokMemoryChatClient")
    @Autowired
    ChatClient grokMemoryChatClient;

    @Qualifier("grokJdbcMemoryChatClient")
    @Autowired
    ChatClient grokJdbcMemoryChatClient;


    @GetMapping("chat")
    public ResponseEntity<String> memory(@RequestParam String username, @RequestParam String message) {
        return ResponseEntity.ok(grokMemoryChatClient
                .prompt()
                .user(message)
                .advisors(advisorSpec -> advisorSpec
                        .param(ChatMemory.CONVERSATION_ID, username))
                .call()
                .content());
    }

    @GetMapping("chat2")
    public ResponseEntity<String> jdbcMemory(@RequestParam String username, @RequestParam String message) {
        return ResponseEntity.ok(grokJdbcMemoryChatClient
                .prompt()
                .user(message)
                .advisors(advisorSpec -> advisorSpec
                        .param(ChatMemory.CONVERSATION_ID, username))
                .call()
                .content());
    }
}
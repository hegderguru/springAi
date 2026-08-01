
package com.gunitha.springai.controller.basic;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/options/v1")
public class GrokOptionsChatController {

    @Qualifier("grokOptionsChatClient")
    @Autowired
    ChatClient grokOptionsChatClient;

    @GetMapping("chat")
    public String chat(@RequestParam String message) {
        return grokOptionsChatClient.prompt(message)
                .options(OpenAiChatOptions.builder().model("llama-3.3-70b-versatile").maxCompletionTokens(100))
                .system("You are AI assistant. You answer questions related to Spring boot Java implementation")
                .call().content();
    }
}

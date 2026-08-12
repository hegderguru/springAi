package com.gunitha.springai.controller.tool;

import com.gunitha.springai.tool.TicketTool;
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

import java.util.Map;

@RestController
@RequestMapping("api/tools")
public class TicketController {

    @Qualifier("ticketChatClient")
    @Autowired
    private ChatClient ticketChatClient;

    @Autowired
    TicketTool  ticketTool;

    @GetMapping("ticket")
    public Mono<String> time(@RequestParam("username") String username, @RequestParam String message) {
        return Mono.fromCallable(() -> {
            return ticketChatClient.prompt()
                    .advisors(advisorSpec -> advisorSpec.param(ChatMemory.CONVERSATION_ID,username))
                    .user(message)
                    .tools(ticketTool)
                    .toolContext(Map.of("username",username))
                    .call()
                    .content();
        }).subscribeOn(Schedulers.boundedElastic());
    }
}

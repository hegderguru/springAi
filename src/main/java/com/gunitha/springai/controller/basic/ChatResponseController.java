package com.gunitha.springai.controller.basic;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.converter.ListOutputConverter;
import org.springframework.ai.converter.MapOutputConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/response/v1")
public class ChatResponseController {

    @Autowired
    ChatClient chatClient;

    @GetMapping("chat")
    public Flux<String> response(String message) {
        return chatClient.prompt(message)
                .system("You are AI assistant. You answer questions related to Spring boot Java implementation")
                .stream().content();
    }

    @GetMapping("chat-structured")
    public ResponseEntity<SpringDetail> responseStructured(String message) {
        SpringDetail entity = chatClient.prompt(message)
                .advisors(new SimpleLoggerAdvisor())
                .system("You are AI assistant. You answer questions related to Spring boot Java implementation")
                .call().entity(SpringDetail.class);
        return ResponseEntity.ok(entity);
    }

    record SpringDetail(String springBootVersion, String yearOfRelease, String javaVersionSupport){};

    @GetMapping("chat-structured-list")
    public ResponseEntity<List<String>> responseStructuredList(String message) {
        List<String> entity = chatClient.prompt(message)
                .advisors(new SimpleLoggerAdvisor())
                .system("You are AI assistant. You answer questions related to Spring boot Java implementation")
                .call().entity(new ListOutputConverter());
        return ResponseEntity.ok(entity);
    }

    @GetMapping("chat-structured-map")
    public ResponseEntity<Map<String,Object>> responseStructuredMap(String message) {
        Map<String,Object> entity = chatClient.prompt(message)
                .advisors(new SimpleLoggerAdvisor())
                .system("You are AI assistant. You answer questions related to Spring boot Java implementation")
                .call().entity(new MapOutputConverter());
        return ResponseEntity.ok(entity);
    }

    @GetMapping("chat-structured-pojo")
    public ResponseEntity<SpringDetail> responseStructuredBean(String message) {
        SpringDetail entity = chatClient.prompt(message)
                .advisors(new SimpleLoggerAdvisor())
                .system("You are AI assistant. You answer questions related to Spring boot Java implementation")
                .call().entity(new BeanOutputConverter<>(SpringDetail.class));
        return ResponseEntity.ok(entity);
    }

    @GetMapping("chat-structured-pojo-list")
    public ResponseEntity<List<SpringDetail>> responseStructuredListBean(String message) {
        List<SpringDetail> entity = chatClient.prompt(message)
                .advisors(new SimpleLoggerAdvisor())
                .system("You are AI assistant. You answer questions related to Spring boot Java implementation")
                .call().entity(new ParameterizedTypeReference<List<SpringDetail>>() {
                });
        return ResponseEntity.ok(entity);
    }

}

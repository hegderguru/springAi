package com.gunitha.springai.config.memory;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.memory.ChatMemoryRepository;
import org.springframework.ai.chat.memory.InMemoryChatMemoryRepository;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;

@Primary
@Repository
@RequiredArgsConstructor
public class CustomInMemoryChatMemoryRepository implements ChatMemoryRepository {

    InMemoryChatMemoryRepository delegate = new InMemoryChatMemoryRepository();

    @Override
    public List<String> findConversationIds() {
        return delegate.findConversationIds();
    }

    @Override
    public List<Message> findByConversationId(String conversationId) {
        List<Message> messages = delegate.findByConversationId(conversationId);
       /* messages.forEach(message -> {
            if (message instanceof AssistantMessage assistantMessage) {
                assistantMessage.getMetadata().remove("reasoning_content");
            }
        });*/
        return messages;
    }

    @Override
    public void saveAll(String conversationId, List<Message> messages) {
        messages.removeIf(message -> message instanceof AssistantMessage);
        delegate.saveAll(conversationId, messages);
    }

    @Override
    public void deleteByConversationId(String conversationId) {
        delegate.deleteByConversationId(conversationId);
    }
}

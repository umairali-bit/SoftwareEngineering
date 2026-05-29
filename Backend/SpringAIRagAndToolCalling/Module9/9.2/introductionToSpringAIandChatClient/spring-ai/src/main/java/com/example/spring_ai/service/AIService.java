package com.example.spring_ai.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AIService {

    private ChatClient chatClient;

    public String getJoke(String topic) {
        return chatClient.prompt()
                .user("Give me a joke on topic: " + topic)
                .call()
                .content();
    }


}

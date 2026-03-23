package com.example.safeAndForgetful.service;


import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.client.advisor.SafeGuardAdvisor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SafeAndForgetfulService {

    private final ChatClient chatClient;
    private final ChatMemory chatMemory;

    public String chat(String prompt, String userId) {
        return chatClient.prompt()
                .system(
                        """
                                You are a friendly assistant
                                Answer concisely
                                """)
                .user(prompt)
                .advisors(
                        new SafeGuardAdvisor(List.of("competitor")),

                        MessageChatMemoryAdvisor.builder(chatMemory)
                                .conversationId(userId)
                                .build()
                )
                .call()
                .content();
    }
}

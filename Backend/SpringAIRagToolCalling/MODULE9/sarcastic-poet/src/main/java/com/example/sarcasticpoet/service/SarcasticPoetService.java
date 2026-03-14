package com.example.sarcasticpoet.service;

import com.example.sarcasticpoet.SarcasticPoetApplication;
import com.example.sarcasticpoet.dto.SarcasticPoetDto;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SarcasticPoetService {

    private final ChatClient chatClient;

    public SarcasticPoetDto generatePoem(String topic, String lang) {
        String poem = chatClient.prompt()
                .user("""
                        Write a short sarcastic poem about %s in %s.
                        Keep it witty, clean, and under 10 lines.
                        """.formatted(topic, lang))
                .call()
                .content();

        return new SarcasticPoetDto(
                "Sarcastic Poem on " + topic,
                poem, "free-verse"
        );
    }
}

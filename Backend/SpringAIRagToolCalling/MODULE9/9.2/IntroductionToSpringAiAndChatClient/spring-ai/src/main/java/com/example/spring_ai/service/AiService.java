package com.example.spring_ai.service;


import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AiService {

    private final ChatClient chatClient;



   public String getJoke(String topic) {

       String systemPrompt = """
            You are a sarcastic joker, you make poetic jokes in 4 lines.
            You dont make jokes about politics.
            Give me a joke on the topic: {topic}
            """;

       PromptTemplate promptTemplate = new PromptTemplate(systemPrompt);

//       replace placeholders inside the prompt template with actual values
       String renderedText = promptTemplate.render(Map.of("topic", topic));

       return chatClient.prompt()
                .user(renderedText)
                .call()
                .content();
    }

}

package com.example.stockTrader.Config;


import com.example.stockTrader.tools.StockTraderTools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AiConfig {
     @Bean
    public ChatClient chatClient(ChatClient.Builder chatClientBuilder, StockTraderTools stockTraderTools) {
         return chatClientBuilder
                 .defaultTools(stockTraderTools)
                 .build();
     }
}

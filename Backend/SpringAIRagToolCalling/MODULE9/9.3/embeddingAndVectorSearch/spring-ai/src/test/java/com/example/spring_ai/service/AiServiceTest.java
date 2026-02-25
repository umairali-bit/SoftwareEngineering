package com.example.spring_ai.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class AiServiceTest {

    @Autowired
    private AiService aiService;


    @Test
    public void testGetJoke(){
        var joke = aiService.getJoke("Breaking Bad");
        System.out.println(joke);
    }

    @Test
    public void testEmbedText(){
        var embed = aiService.getEmbedding("Stay out of my territory.");
        System.out.println(embed.length);

        for(float e : embed){
            System.out.print(e + " ");
        }
    }

    @Test
    public void testStoreData(){
//        aiService.ingestDataToVectorStore(
//                """
//                    I am not in danger, Skyler. I am the danger. A guy opens " +
//                    "his door and gets shot, and you think that of me? No. I am the one who knocks!
//                    """);

        aiService.ingestDataToVectorStore();

    }

}

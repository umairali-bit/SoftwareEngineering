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
        var joke = aiService.getJoke("Programmer");
        System.out.println(joke);
    }

    @Test
    public void testEmbedText(){
        var embed = aiService.getEmbedding("There is no embedding");
        System.out.println(embed.length);

        for(float e : embed){
            System.out.print(e + " ");
        }
    }

}

package com.example.spring_ai.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RAGServiceTests {

    @Autowired
    private RAGService ragService;

    @Test
    public void testRAGService() {
        var resp = ragService.askAI("How many seasons does breaking bad has?");
        System.out.println(resp);
    }

    @Test
    public void testRAGIngest() {
        ragService.ingestPdfToVectorStore();

    }
}

package com.example.spring_ai.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RAGServiceTests {

    @Autowired
    RAGService ragService;

//    @Test
//    public void testIngestionToVectorStore() {
//        ragService.ingestPdfToVectorStore();
//    }

//    @Test
//    public void testAskAI() {
//
//        var resp = ragService.askAI("What is RAG");
//        System.out.println(resp);
//    }

    @Test
    public void testAskAiWithAdvisors() {

        var resp = ragService.askAiWithAdvisors("What is RAG?", "Hank123");
        System.out.println(resp);
    }
}

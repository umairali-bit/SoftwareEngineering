package com.example.employee_handbook.config;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PdfIngestionRunner implements CommandLineRunner {

    private final VectorStore vectorStore;

    @Override
    public void run(String... args) throws Exception {

        //ETL Extract, Transform, Load


        // 1. Check if already ingested
        if (!vectorStore.similaritySearch(
                SearchRequest.builder().query("policy").topK(1).build()
        ).isEmpty()) {
            System.out.println("Already ingested, skipping...");
            return;
        }

        // read PDF
        // split text
        // vectorStore.add(...)

        System.out.println("Starting PDF ingestion...");

        System.out.println("PDF ingestion complete");
    }


}

package com.example.ai.vibe.controller;

import com.example.ai.vibe.service.AiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class vibeController {

    private final AiService aiService;

    @PostMapping("/ingest")
    public String ingestSong() {
        aiService.ingestDataToVectorStore();
        return "Success";
    }
}

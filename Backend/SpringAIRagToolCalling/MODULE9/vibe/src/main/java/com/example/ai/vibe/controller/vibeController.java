package com.example.ai.vibe.controller;

import com.example.ai.vibe.dto.SongMatchResponse;
import com.example.ai.vibe.service.AiService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.document.Document;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
@RequiredArgsConstructor
public class vibeController {

    private final AiService aiService;

    @PostMapping("/ingest")
    public String ingestSong() {
        return aiService.ingestDataToVectorStore();

    }

    @GetMapping("/match-vibe")
    public List<SongMatchResponse>  matchVibe(@RequestParam String feeling) {
        List<Document> matches = aiService.similaritySearch(feeling);

        return matches.stream()
                .map(document -> new SongMatchResponse(
                        (String) document.getMetadata().get("title"),
                        (String) document.getMetadata().get("artist"),
                        (String) document.getMetadata().get("genre"),
                        document.getText()


                )).toList();
    }
}

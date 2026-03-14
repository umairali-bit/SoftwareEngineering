package com.example.sarcasticpoet.controller;


import com.example.sarcasticpoet.dto.SarcasticPoetDto;
import com.example.sarcasticpoet.service.SarcasticPoetService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SarcasticPoetController {
    private final SarcasticPoetService poetService;

    @GetMapping("/poem")
    public SarcasticPoetDto getSarcasticPoem(
            @RequestParam String topic,
            @RequestParam String lang
    ) {
        return poetService.generatePoem(topic, lang);

    }

}

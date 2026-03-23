package com.example.safeAndForgetful.controller;

import com.example.safeAndForgetful.service.SafeAndForgetfulService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private final SafeAndForgetfulService safeAndForgetfulService;

    @PostMapping("/chat")
    public String chat(@RequestParam String userId,
                       @RequestBody String prompt) {
        return safeAndForgetfulService.chat(prompt, userId);
    }
}

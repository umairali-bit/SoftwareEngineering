package com.umair.subscription.controllers;


import com.umair.subscription.dto.SignupRequestDTO;
import com.umair.subscription.dto.UserDTO;
import com.umair.subscription.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<SignupRequestDTO> signup(@RequestBody @Valid SignupRequestDTO request) {
        UserDTO user= userService.registerWithFreePlan(request);

        return ResponseEntity.ok().body(request);


    }
}

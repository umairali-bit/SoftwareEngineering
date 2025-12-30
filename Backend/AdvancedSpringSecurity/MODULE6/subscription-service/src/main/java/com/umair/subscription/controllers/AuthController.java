package com.umair.subscription.controllers;


import com.umair.subscription.dto.*;
import com.umair.subscription.entities.UserEntity;
import com.umair.subscription.services.AuthService;
import com.umair.subscription.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<UserDTO> signup(
            @Valid @RequestBody SignupRequestDTO request
    ) {
        UserDTO user = userService.registerWithFreePlan(request);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO request) {
        return  ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/refresh")
    public ResponseEntity<RefreshResponseDTO> refresh(@RequestBody RefreshRequestDTO request) {
        return ResponseEntity.ok(authService.refresh(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody LogoutRequestDTO request) {
        authService.logout(request);
        return ResponseEntity.ok(Map.of("message", "Logged out successfully"));
    }

    @PostMapping("/logout-all")
    public ResponseEntity<?> logoutAll(Authentication authentication) {

        if (authentication == null || !(authentication.getPrincipal() instanceof UserEntity user)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Not authenticated"));
        }
        user = (UserEntity) authentication.getPrincipal();
        int count = authService.logoutAllDevices(user.getId());

        return ResponseEntity.ok(Map.of("message", "Logged out from all devices", "revoked", String.valueOf(count)));
    }

    @PostMapping("/logout-all-test")
    public ResponseEntity<?> logoutAllTest(@RequestParam Long userId) {
        int count = authService.logoutAllDevices(userId);
        return ResponseEntity.ok(Map.of("revoked", count));
    }

}

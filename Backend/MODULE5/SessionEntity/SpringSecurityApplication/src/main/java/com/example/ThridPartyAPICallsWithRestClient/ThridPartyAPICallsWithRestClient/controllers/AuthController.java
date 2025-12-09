package com.example.ThridPartyAPICallsWithRestClient.ThridPartyAPICallsWithRestClient.controllers;


import com.example.ThridPartyAPICallsWithRestClient.ThridPartyAPICallsWithRestClient.dtos.LoginDTO;
import com.example.ThridPartyAPICallsWithRestClient.ThridPartyAPICallsWithRestClient.dtos.SignUpDTO;
import com.example.ThridPartyAPICallsWithRestClient.ThridPartyAPICallsWithRestClient.dtos.UserDto;
import com.example.ThridPartyAPICallsWithRestClient.ThridPartyAPICallsWithRestClient.services.AuthService;
import com.example.ThridPartyAPICallsWithRestClient.ThridPartyAPICallsWithRestClient.services.SessionService;
import com.example.ThridPartyAPICallsWithRestClient.ThridPartyAPICallsWithRestClient.services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/auth")

public class AuthController {

    private final UserService userService;
    private final AuthService authService;
    private final SessionService sessionService;



    @PostMapping("/signUp")
    public ResponseEntity<UserDto> signUp(@RequestBody @Valid SignUpDTO inputSignUp) {

        UserDto userDto = userService.singUp(inputSignUp);
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO inputLogin, HttpServletRequest request,
                                        HttpServletResponse response) {
        String token = authService.login(inputLogin);
        Cookie cookie = new Cookie("token", token);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);


        return ResponseEntity.ok(token);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {

        String token = null;

        //1. Try extracting JWT token from Authorization header
        String header = request.getHeader("Authorization");
        if(header != null && header.startsWith("Bearer ")) {
            token = header.substring(7);
        }

        //2. try to find it in cookie
        if(token == null && request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if(cookie.getName().equals("token")) {
                    token = cookie.getValue();
                }
            }
        }

        if (token != null) {
            sessionService.revokeByToken(token);
        }

        //3 delete cookie
        Cookie deleteCookie = new Cookie("token", null);
        deleteCookie.setHttpOnly(true);
        deleteCookie.setMaxAge(0);
        deleteCookie.setPath("/");
        response.addCookie(deleteCookie);


        return ResponseEntity.ok("Logged out successfully");

    }


}

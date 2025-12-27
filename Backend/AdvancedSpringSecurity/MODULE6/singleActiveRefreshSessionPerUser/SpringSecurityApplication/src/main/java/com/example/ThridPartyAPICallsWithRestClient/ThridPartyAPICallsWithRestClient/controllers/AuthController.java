package com.example.ThridPartyAPICallsWithRestClient.ThridPartyAPICallsWithRestClient.controllers;


import com.example.ThridPartyAPICallsWithRestClient.ThridPartyAPICallsWithRestClient.dtos.LoginDTO;
import com.example.ThridPartyAPICallsWithRestClient.ThridPartyAPICallsWithRestClient.dtos.LoginResponseDTO;
import com.example.ThridPartyAPICallsWithRestClient.ThridPartyAPICallsWithRestClient.dtos.SignUpDTO;
import com.example.ThridPartyAPICallsWithRestClient.ThridPartyAPICallsWithRestClient.dtos.UserDto;
import com.example.ThridPartyAPICallsWithRestClient.ThridPartyAPICallsWithRestClient.services.AuthService;
import com.example.ThridPartyAPICallsWithRestClient.ThridPartyAPICallsWithRestClient.services.UserService;
import com.example.ThridPartyAPICallsWithRestClient.ThridPartyAPICallsWithRestClient.dtos.LoginResponseDTO;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(path = "/auth")

public class AuthController {

    private final UserService userService;
    private final AuthService authService;

    @Value("${deploy.env}")
    private String deployEnv;


    @PostMapping("/signUp")
    public ResponseEntity<UserDto> signUp(@RequestBody @Valid SignUpDTO inputSignUp) {

        UserDto userDto = userService.singUp(inputSignUp);
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginDTO inputLogin, HttpServletRequest request,
                                        HttpServletResponse response) {
        LoginResponseDTO login = authService.login(inputLogin);
        Cookie cookie = new Cookie("refreshToken", login.getRefreshToken());
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setSecure("production".equals(deployEnv));
        response.addCookie(cookie);


        return ResponseEntity.ok(login);
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDTO> refresh(HttpServletRequest request,
                                                    HttpServletResponse response) {
        log.info("deployEnv={}", deployEnv);

        Cookie[] cookies = request.getCookies();

        if (cookies == null || cookies.length == 0) {
            throw new AuthenticationServiceException("No cookies found on request");
        }

        for (Cookie c : cookies) {
            log.info("Cookie received: name=[{}], value=[{}], path=[{}]",
                    c.getName(), c.getValue(), c.getPath());
        }


        String refreshToken = Arrays.stream(cookies)
                .filter(cookie -> "refreshToken".equals(cookie.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow(() -> new AuthenticationServiceException("Refresh token not found"));

        log.info("Refresh token from cookie = [{}], length={}", refreshToken, refreshToken.length());


        LoginResponseDTO loginResponseDTO = authService.refreshToken(refreshToken);

//      MINIMAL REQUIRED FOR ROTATION: overwrite cookie with NEW refresh token
        Cookie newCookie = new Cookie("refreshToken", loginResponseDTO.getRefreshToken());
        newCookie.setHttpOnly(true);
        newCookie.setSecure(true);
        newCookie.setPath("/");
        response.addCookie(newCookie);

       return ResponseEntity.ok(loginResponseDTO);
    }






}

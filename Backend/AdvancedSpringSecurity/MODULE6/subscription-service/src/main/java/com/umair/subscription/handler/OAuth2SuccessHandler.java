package com.umair.subscription.handler;

import com.umair.subscription.entities.SessionEntity;
import com.umair.subscription.entities.UserEntity;
import com.umair.subscription.entities.enums.SessionStatus;
import com.umair.subscription.repositories.SessionRepository;
import com.umair.subscription.repositories.UserRepository;
import com.umair.subscription.services.JWTService;
import com.umair.subscription.services.RefreshTokenHasher;
import com.umair.subscription.services.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.message.LoggerNameAwareMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;
    private final JWTService jwtService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");

        if (email == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\":\"Email not found from provider\"}");
            return;
        }

        // 1) find or create local user
        UserEntity user = userRepository.findByEmail(email)
                .orElseGet(() -> {
                    UserEntity u = new UserEntity();
                    u.setEmail(email);
                    u.setName(name != null ? name : "Unknown");
                    u.setPassword("OAUTH2"); // placeholder (or null) since no local password
                    return userRepository.save(u);
                });

        // 2) issue tokens
        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        // 3) save session (refresh token hash)
        SessionEntity session = new SessionEntity();
        session.setUser(user);
        session.setRefreshTokenHash(RefreshTokenHasher.sha256(refreshToken));
        session.setStatus(SessionStatus.ACTIVE);
        session.setExpiresAt(LocalDateTime.now().plusDays(30));
        sessionRepository.save(session);

        //passing the token to frontend
        String frontEndUrl = "http://localhost:8080/home.html?token="+accessToken;
        getRedirectStrategy().sendRedirect(request, response, frontEndUrl);

    }
}
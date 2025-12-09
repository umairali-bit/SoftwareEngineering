package com.example.ThridPartyAPICallsWithRestClient.ThridPartyAPICallsWithRestClient.filters;

import com.example.ThridPartyAPICallsWithRestClient.ThridPartyAPICallsWithRestClient.entities.UserEntity;
import com.example.ThridPartyAPICallsWithRestClient.ThridPartyAPICallsWithRestClient.exceptions.ResourceNotFoundException;
import com.example.ThridPartyAPICallsWithRestClient.ThridPartyAPICallsWithRestClient.services.JwtService;
import com.example.ThridPartyAPICallsWithRestClient.ThridPartyAPICallsWithRestClient.services.SessionService;
import com.example.ThridPartyAPICallsWithRestClient.ThridPartyAPICallsWithRestClient.services.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserService userService;
    private final SessionService sessionService;

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver exceptionResolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            final String requestTokenHeader = request.getHeader("Authorization");

//      if we dont get the token
            // 1) No header or wrong format -> let the request pass as anonymous
            if (requestTokenHeader == null || !requestTokenHeader.startsWith("Bearer ")) {

                filterChain.doFilter(request, response);

                return;

            }

//      if we get the token
            // 2) Extract token safely
            String token = requestTokenHeader.split("Bearer ")[1];

            // 3) Check if this token is still active in DB
            if (!sessionService.isTokenActive(token)) {
                throw new ResourceNotFoundException("Session for this token is not active.");
            }


            // 4) Extract userId from JWT (this also validates signature & expiry)
            Long userId = jwtService.getUserIdFromJwtToken(token);

            // 5) If we have userId and no Authentication yet, authenticate
            if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserEntity user = userService.getUserById(userId);

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken
                        (user, null, user.getAuthorities());
                authentication.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                SecurityContextHolder.getContext().setAuthentication((authentication));
            }
            // 6) Continue the filter chain
            filterChain.doFilter(request, response);
        } catch (Exception ex) {
            System.out.println("JWT ERROR in filter: " + ex.getClass().getSimpleName() + " - " + ex.getMessage());
            exceptionResolver.resolveException(request, response, null, ex);

        }


    }

//    @Override
//    protected boolean shouldNotFilter(HttpServletRequest request) {
//        String path = request.getServletPath();
//        return path.startsWith("/auth")
//                || path.equals("/posts");        // or path.startsWith("/posts")
//    }
//
//
}

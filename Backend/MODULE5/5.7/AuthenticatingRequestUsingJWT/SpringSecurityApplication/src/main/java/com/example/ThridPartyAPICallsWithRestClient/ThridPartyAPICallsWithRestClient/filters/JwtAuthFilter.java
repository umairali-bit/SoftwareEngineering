package com.example.ThridPartyAPICallsWithRestClient.ThridPartyAPICallsWithRestClient.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtAuthFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final String requestTokenHeader = request.getHeader("Authorization");

        //if we dont get the token
        if (requestTokenHeader != null && !requestTokenHeader.startsWith("Bearer ")) {

            filterChain.doFilter(request, response);
            return;

        }

    }
}

package com.example.ThridPartyAPICallsWithRestClient.ThridPartyAPICallsWithRestClient.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;

@Slf4j
@Component
public class LoggingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        ContentCachingRequestWrapper requestWrapper =
                new ContentCachingRequestWrapper(request, 1024 * 1024);
        ContentCachingResponseWrapper responseWrapper =
                new ContentCachingResponseWrapper(response);

        long start = System.currentTimeMillis();

        try {
            filterChain.doFilter(requestWrapper, responseWrapper);
        } finally {

            long duration = System.currentTimeMillis() - start;

            String headers = getHeadersWithMasking(requestWrapper);
            String requestBody = getRequestBody(requestWrapper);
            String responseBody = getResponseBody(responseWrapper);

            // REQUEST LOG
            log.info("REQUEST [{}] {} query={} headers={} body={}",
                    request.getMethod(),
                    request.getRequestURI(),
                    request.getQueryString(),
                    headers,
                    requestBody
            );

            // RESPONSE LOG
            log.info("RESPONSE [{}] {} status={} duration={}ms body={}",
                    request.getMethod(),
                    request.getRequestURI(),
                    responseWrapper.getStatus(),
                    duration,
                    responseBody
            );

            responseWrapper.copyBodyToResponse();
        }
    }

    private String getRequestBody(ContentCachingRequestWrapper requestWrapper) {
        byte[] buffer = requestWrapper.getContentAsByteArray();
        if (buffer.length == 0) {
            return "";
        }

        return  new String(buffer, StandardCharsets.UTF_8);
    }

    private String getResponseBody(ContentCachingResponseWrapper responseWrapper) {
        byte[] buffer = responseWrapper.getContentAsByteArray();
        if (buffer.length == 0) {
            return "";
        }
        return new String(buffer, StandardCharsets.UTF_8);

    }

    private String getHeadersWithMasking(HttpServletRequest request) {
        StringBuilder stringBuilder = new StringBuilder("{");
        Enumeration<String> headerNames = request.getHeaderNames();
        boolean first = true;

        if(headerNames == null) return "{}";

        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            String value = request.getHeader(name);

            //Mask the Authorization header
            if ("authorization".equalsIgnoreCase(name)) {
                value = maskAuthorization(value);
            }

            if(!first) stringBuilder.append(", ");
            first = false;
            stringBuilder.append(name).append("=").append(value);
        }

        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    private String maskAuthorization(String value) {
        if (value == null || value.isBlank()) {
            return "******";
        }

        // Example: "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
        int idx = value.indexOf(' ');
        if (idx > 0) {
            String scheme = value.substring(0, idx);  // "Bearer"
            return scheme + " ******";                // "Bearer ******"
        }

        // If no space (weird format), just hide everything
        return "******";
    }




}

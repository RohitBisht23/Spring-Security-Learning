package com.SpringSecurity.SecurityAppLearning.SecurityAppLearning.JwtFilters;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class LoggerFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(LoggerFilter.class);

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private final HandlerExceptionResolver handlerExceptionResolver;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            // Wrap request and response to allow reading multiple times
            ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
            ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);

            // Proceed with filter chain
            filterChain.doFilter(wrappedRequest, wrappedResponse);

            // Log request details
            logRequest(wrappedRequest);

            // Log response details
            logResponse(wrappedResponse);

            // Copy response back to the original output stream
            wrappedResponse.copyBodyToResponse();
        } catch(Exception e) {
            handlerExceptionResolver.resolveException(request, response, null, e);
        }
    }


    private void logRequest(ContentCachingRequestWrapper request) {
        String requestBody = new String(request.getContentAsByteArray(), StandardCharsets.UTF_8);
        logger.info("Incoming Request: [{} {}] Body: {}", request.getMethod(), request.getRequestURI(), requestBody);
    }

    private void logResponse(ContentCachingResponseWrapper response) {
        String responseBody = new String(response.getContentAsByteArray(), StandardCharsets.UTF_8);
        logger.info("Outgoing Response: [{}] Token: {}", response.getStatus(), responseBody);
    }
}

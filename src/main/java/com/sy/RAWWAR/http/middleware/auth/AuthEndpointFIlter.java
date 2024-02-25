package com.sy.RAWWAR.http.middleware;

import java.io.IOException;

import org.springframework.web.filter.OncePerRequestFilter;

import com.sy.RAWWAR.model.ApiConfig;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AuthEndpointFIlter extends OncePerRequestFilter {

    private final ApiConfig apiConfig;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        if (request.getHeader("x-api-key").equals(apiConfig.getApiKey()))
            filterChain.doFilter(request, response);
        else
            throw new IllegalStateException("Token non valido");
    }

}

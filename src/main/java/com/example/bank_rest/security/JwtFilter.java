package com.example.bank_rest.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.bank_rest.exception.handler.ErrorResponse;
import com.example.bank_rest.repository.InvalidTokenRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    private final InvalidTokenRepository invalidTokenRepository;
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI();
        log.info("Class: JwtFilter, method: doFilterInternal, path: {}", path);

        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            log.debug("Skipping JWT check for OPTIONS preflight request");
            filterChain.doFilter(request, response);
            return;
        }

        if (path.startsWith("/api/v1/auth")
                || path.startsWith("/api/v1/signup")
                || path.startsWith("/v3/api-docs")
                || path.startsWith("/swagger-ui")
                || path.startsWith("/swagger-ui.html")
                || path.startsWith("/webjars")
        ) {
            log.info("Skipping JWT check for public endpoint: {}", path);
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || authHeader.isBlank() || !authHeader.startsWith("Bearer ")) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing or invalid JWT");
            return;
        }

        String jwt = authHeader.substring(7);
        try {

            String username = jwtUtil.validateTokenAndRetrieveSubject(jwt);

            if (invalidTokenRepository.existsByToken(jwt)) {
                log.warn("Token is invalidated: {}", jwt);
                sendErrorResponse(response, HttpStatus.UNAUTHORIZED, "Token is expired", path);
                return;
            }
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authToken);
            log.info("Successfully authenticated user: {}", username);
            filterChain.doFilter(request, response);
        } catch (JWTVerificationException exc) {
            log.error("Invalid JWT: {}", exc.getMessage());
            sendErrorResponse(response, HttpStatus.UNAUTHORIZED, "Invalid JWT", path);
        } catch (UsernameNotFoundException exc) {
            log.error("User not found: {}", exc.getMessage());
            sendErrorResponse(response, HttpStatus.UNAUTHORIZED, "User not found", path);
        }
    }

    private void sendErrorResponse(HttpServletResponse response, HttpStatus status, String message, String path)
            throws IOException {
        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        ErrorResponse errorResponse = new ErrorResponse(
                status,
                message,
                null,
                path,
                LocalDateTime.now()
        );
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }

}

package com.luv2code.travelchecker.filter;

import com.luv2code.travelchecker.configuration.JwtConfiguration;
import com.luv2code.travelchecker.domain.User;
import com.luv2code.travelchecker.exception.EntityNotFoundException;
import com.luv2code.travelchecker.service.UserService;
import com.luv2code.travelchecker.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    public static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthorizationFilter.class);

    private final UserService userService;

    private final UserDetailsService userDetailsService;

    private final JwtConfiguration jwtConfiguration;

    @Autowired
    public JwtAuthorizationFilter(final AuthenticationManager authenticationManager,
                                  final UserService userService,
                                  final UserDetailsService userDetailsService,
                                  final JwtConfiguration jwtConfiguration) {
        super(authenticationManager);
        this.userService = userService;
        this.userDetailsService = userDetailsService;
        this.jwtConfiguration = jwtConfiguration;
    }

    @Override
    protected void doFilterInternal(final HttpServletRequest request,
                                    final HttpServletResponse response,
                                    final FilterChain filterChain) throws ServletException, IOException {
        final String authorizationHeader = request.getHeader(jwtConfiguration.getHeaderName());

        String username = null;
        String jtwToken = null;

        if (authorizationHeader != null && authorizationHeader.startsWith(jwtConfiguration.getTokenPrefix()) && authorizationHeader.length() > 7) {
            jtwToken = authorizationHeader.substring(7);
            try {
                username = JwtUtil.extractUsername(jtwToken, jwtConfiguration.getSecret());
            } catch (final Exception exception){
                final String url = request.getRequestURL().toString();
                if (exception instanceof MalformedJwtException){
                    LOGGER.error("User with malformed JWT signature attempted to access URL: ´{}´.", url);
                }

                throw exception;
            }
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            final UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            final Boolean isTokenValid = JwtUtil.validateToken(jtwToken, userDetails, jwtConfiguration.getSecret());

            User user;
            try {
                user = userService.findByUsername(username);
            } catch (final EntityNotFoundException exception) {
                LOGGER.info("Authorization attempt with valid JWT but non existing username ´{}´.", username);
                throw exception;
            }

            final String url = request.getRequestURL().toString();
            if(!isTokenValid){
                LOGGER.error("User with id ´{}´ attempted access to URL ´{}´, with expired JWT token.", user.getId(), url);
                throw new ExpiredJwtException(null, null, "Expired JWT");
            }

            LOGGER.info("User with id: ´{}´ accessed URL: ´{}´.", user.getId(), url);

            final UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }

        filterChain.doFilter(request, response);
    }
}

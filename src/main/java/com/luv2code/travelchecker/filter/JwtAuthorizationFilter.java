package com.luv2code.travelchecker.filter;

import com.luv2code.travelchecker.domain.User;
import com.luv2code.travelchecker.exception.EntityNotFoundException;
import com.luv2code.travelchecker.service.UserService;
import com.luv2code.travelchecker.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
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

import static com.luv2code.travelchecker.util.SecurityConstants.*;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    public static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthorizationFilter.class);

    private final UserService userService;

    private final UserDetailsService userDetailsService;

    @Autowired
    public JwtAuthorizationFilter(final AuthenticationManager authenticationManager,
                                  final UserService userService,
                                  final UserDetailsService userDetailsService) {
        super(authenticationManager);
        this.userService = userService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(final HttpServletRequest request,
                                    final HttpServletResponse response,
                                    final FilterChain filterChain) throws ServletException, IOException {
        final String authorizationHeader = request.getHeader(HEADER_NAME);

        String email = null;
        String jtwToken = null;

        if (authorizationHeader != null && authorizationHeader.startsWith(TOKEN_PREFIX) && authorizationHeader.length() > 7) {
            jtwToken = authorizationHeader.substring(7);
            try {
                email = JwtUtil.extractUsername(jtwToken, SECRET);
            } catch (final Exception exception){
                final String url = request.getRequestURL().toString();
                if(exception instanceof SignatureException){
                    LOGGER.info("User with invalid JWT signature attempted to access URL: {}.", url);
                } else if (exception instanceof MalformedJwtException){
                    LOGGER.info("User with malformed JWT signature attempted to access URL: {}.", url);
                }

                throw exception;
            }
        }

        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            final UserDetails userDetails = this.userDetailsService.loadUserByUsername(email);
            final Boolean isTokenValid = JwtUtil.validateToken(jtwToken, userDetails, SECRET);

            User user;
            try {
                user = userService.findByEmail(email);
            } catch (final EntityNotFoundException exception) {
                LOGGER.info("Authorization attempt with valid JWT but non existing email: ´{}´.", email);
                throw exception;
            }

            final String url = request.getRequestURL().toString();
            if(!isTokenValid){
                LOGGER.error("User with id: ´{}´ attempted access to URL: ´{}´, with expired JWT token.", user.getId(), url);
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

package com.luv2code.travelchecker.filter;

import com.luv2code.travelchecker.configuration.JwtProperties;
import com.luv2code.travelchecker.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    public static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthorizationFilter.class);

    private final UserDetailsService userDetailsService;

    private final JwtProperties jwtProperties;

    @Autowired
    public JwtAuthorizationFilter(final AuthenticationManager authenticationManager,
                                  final UserDetailsService userDetailsService,
                                  final JwtProperties jwtProperties) {
        super(authenticationManager);
        this.userDetailsService = userDetailsService;
        this.jwtProperties = jwtProperties;
    }

    @Override
    protected void doFilterInternal(final HttpServletRequest request,
                                    final HttpServletResponse response,
                                    final FilterChain filterChain) throws ServletException, IOException {
        try {
            final String jwtToken = extractJwtFromRequest(request);
            if (StringUtils.hasText(jwtToken) && JwtUtil.validateJwtToken(jwtToken, jwtProperties.getSecret())) {
                final String username = JwtUtil.extractUsername(jwtToken, jwtProperties.getSecret());
                final UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

                final UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        } catch (final BadCredentialsException ex) {
            LOGGER.error("Authentication attempt with data which could not be parsed to AuthenticationRequest.");
            throw new BadCredentialsException("Data sent for authentication in could not be parsed.");
        } catch (final Exception e) {
            LOGGER.error("Cannot set user authentication: {}.", e.getMessage());
            throw new RuntimeException("Cannot set user authentication.");
        }

        filterChain.doFilter(request, response);
    }

    private String extractJwtFromRequest(final HttpServletRequest httpServletRequest) {
        final String bearerToken = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        return null;
    }
}

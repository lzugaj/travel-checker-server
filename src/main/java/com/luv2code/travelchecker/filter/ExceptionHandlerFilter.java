package com.luv2code.travelchecker.filter;

import com.luv2code.travelchecker.exception.EntityNotFoundException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ExceptionHandlerFilter extends OncePerRequestFilter {

    private final static Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlerFilter.class);

    @Override
    public void doFilterInternal(final HttpServletRequest request,
                                 final HttpServletResponse response,
                                 final FilterChain filterChain) {
        try {
            filterChain.doFilter(request, response);
        } catch (final Exception exception) {
            if (!(exception instanceof BadCredentialsException ||
                    exception instanceof SignatureException ||
                    exception instanceof MalformedJwtException ||
                    exception instanceof ExpiredJwtException ||
                    exception instanceof EntityNotFoundException)){
                LOGGER.error("Application error in: " + exception.getClass().getName(), exception);
                response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            } else {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
            }
        }
    }

}

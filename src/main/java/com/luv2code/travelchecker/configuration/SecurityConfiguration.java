package com.luv2code.travelchecker.configuration;

import com.luv2code.travelchecker.filter.ExceptionHandlerFilter;
import com.luv2code.travelchecker.filter.JwtAuthenticationFilter;
import com.luv2code.travelchecker.filter.JwtAuthorizationFilter;
import com.luv2code.travelchecker.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.luv2code.travelchecker.util.SecurityConstants.*;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserService userService;

    private final UserDetailsService userDetailsService;

    private final JwtConfiguration jwtConfiguration;

    public SecurityConfiguration(final UserService userService,
                                 final UserDetailsService userDetailsService,
                                 final JwtConfiguration jwtConfiguration) {
        this.userService = userService;
        this.userDetailsService = userDetailsService;
        this.jwtConfiguration = jwtConfiguration;
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .cors()
                    .and()
                .csrf()
                    .disable()
                .addFilter(jwtAuthenticationFilter())
                .addFilter(jwtAuthorizationFilter())
                .addFilterBefore(new ExceptionHandlerFilter(), JwtAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers(AUTHENTICATION_URL, AUTHORIZATION_URL)
                    .permitAll()
                .antMatchers(
                        "/v2/api-docs",
                        "/configuration/ui",
                        "/swagger-resources/**",
                        "/configuration/security",
                        "/swagger-ui.html",
                        "/webjars/**")
                    .permitAll()
                .antMatchers(
                        USER_FIND_BY_ID_URL,
                        USER_UPDATE_URL,
                        MARKER_URL,
                        MAPBOX_URL)
                    .hasAnyRole(USER_ROLE)
                .antMatchers(
                        USER_FIND_BY_ID_URL,
                        USER_FIND_ALL_URL)
                    .hasAnyRole(ADMIN_ROLE)
                .anyRequest()
                    .authenticated()
                .and()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .headers().frameOptions().disable();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(jwtConfiguration.getBcryptRounds());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        final JwtAuthenticationFilter jwtAuthenticationFilter =
                new JwtAuthenticationFilter(authenticationManager(), userService, jwtConfiguration);
        jwtAuthenticationFilter.setFilterProcessesUrl(AUTHENTICATION_URL);
        return jwtAuthenticationFilter;
    }

    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter() throws Exception {
        return new JwtAuthorizationFilter(authenticationManager(), userService, userDetailsService, jwtConfiguration);
    }
}

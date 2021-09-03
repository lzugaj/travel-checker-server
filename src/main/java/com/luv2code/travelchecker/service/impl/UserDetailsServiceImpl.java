package com.luv2code.travelchecker.service.impl;

import com.luv2code.travelchecker.domain.User;
import com.luv2code.travelchecker.exception.EntityNotFoundException;
import com.luv2code.travelchecker.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final Optional<User> searchedUser = userRepository.findByUsername(username);
        if (searchedUser.isPresent()) {
            final Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            searchedUser.get().getRoles().forEach(role -> {
                authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName().name()));
            });

            return new org.springframework.security.core.userdetails.User(searchedUser.get().getUsername(), searchedUser.get().getPassword(), authorities);
        } else {
            LOGGER.error("Cannot find User with username: ´{}´.", username);
            throw  new EntityNotFoundException("User", "username", username);
        }
    }
}
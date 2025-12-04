package com.doganmehmet.AirlineBookingApp.security;

import com.doganmehmet.AirlineBookingApp.exception.NotFoundException;
import com.doganmehmet.AirlineBookingApp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        var user = userRepository.findByEmail(username)
                .orElseThrow(() -> new NotFoundException("User not found: " + username));

        return AuthUser.builder()
                .user(user)
                .build();
    }
}

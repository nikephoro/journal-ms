package com.bootcamp.journal.service.impl;

import com.bootcamp.journal.model.User;
import com.bootcamp.journal.dto.UserDto;
import com.bootcamp.journal.repository.UserRepository;
import com.bootcamp.journal.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) {
                return userRepository.findByEmail(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            }
        };
    }

    @Override
    public UserDto findByUsername(String username){
        Optional<User> user = userRepository.findByUsername(username);
        if(user.isPresent()){
            return UserDto
                    .builder()
                    .id(user.get().getId())
                    .email(user.get().getEmail())
                    .name(user.get().getUsername())
                    .build();
        } else {
            throw new RuntimeException();

        }
    }
}

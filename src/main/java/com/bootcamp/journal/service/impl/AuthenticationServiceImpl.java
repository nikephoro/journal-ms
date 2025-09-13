package com.bootcamp.journal.service.impl;

import com.bootcamp.journal.model.Role;
import com.bootcamp.journal.model.User;
import com.bootcamp.journal.dto.AuthRequestDto;
import com.bootcamp.journal.dto.AuthResponseDto;
import com.bootcamp.journal.dto.UserDto;
import com.bootcamp.journal.repository.UserRepository;
import com.bootcamp.journal.service.AuthenticationService;
import com.bootcamp.journal.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthResponseDto register(AuthRequestDto request) {
        var user = User.builder()
                .email(request.getEmail())
                .username(request.getName())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER).build();
        userRepository.save(user);
        UserDto userDto = UserDto.builder()
                .id(user.getId())
                .name(user.getEmail())
                .email(user.getUsername())
                .build();
        var jwt = jwtService.generateToken(user);
        return AuthResponseDto.builder().token(jwt).user(userDto).build();
    }

    @Override
    public AuthResponseDto login(AuthRequestDto request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));
        var jwt = jwtService.generateToken(user);
        UserDto userDto = UserDto.builder()
                .id(user.getId())
                .name(user.getUsername())
                .email(user.getEmail())
                .build();
        return AuthResponseDto.builder().token(jwt).user(userDto).build();
    }
}

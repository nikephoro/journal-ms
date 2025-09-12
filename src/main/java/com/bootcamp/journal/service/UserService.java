package com.bootcamp.journal.service;

import com.bootcamp.journal.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {
    UserDetailsService userDetailsService();
    UserDto findByUsername(String username);
}

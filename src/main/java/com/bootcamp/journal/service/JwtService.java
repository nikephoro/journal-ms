package com.bootcamp.journal.service;

import com.bootcamp.journal.dao.User;

public interface JwtService {
    String extractUserName(String token);

    String generateToken(User userDetails);

    boolean isTokenValid(String token, User userDetails);
}

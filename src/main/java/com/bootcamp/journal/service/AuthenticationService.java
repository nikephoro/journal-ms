package com.bootcamp.journal.service;

import com.bootcamp.journal.dto.AuthRequestDto;
import com.bootcamp.journal.dto.AuthResponseDto;

public interface AuthenticationService {
    AuthResponseDto register(AuthRequestDto request);

    AuthResponseDto login(AuthRequestDto request);
}

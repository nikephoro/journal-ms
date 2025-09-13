package com.bootcamp.journal.service.impl;

import com.bootcamp.journal.model.User;
import com.bootcamp.journal.dto.UserDto;
import com.bootcamp.journal.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void loadUserByUsername_throwsException_whenUserDoesNotExist() {
        String username = "nonexistent@example.com";

        when(userRepository.findByEmail(username)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class,
                () -> userService.userDetailsService().loadUserByUsername(username));
    }

    @Test
    void findByUsername_returnsUserDto_whenUserExists() {
        String username = "testUser";
        User mockUser = new User();
        mockUser.setId(1);
        mockUser.setUsername(username);
        mockUser.setEmail("test@example.com");

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(mockUser));

        UserDto userDto = userService.findByUsername(username);

        assertNotNull(userDto);
        assertEquals(1, userDto.getId());
        assertEquals("test@example.com", userDto.getEmail());
        assertEquals(username, userDto.getName());
    }

    @Test
    void findByUsername_throwsException_whenUserDoesNotExist() {
        String username = "nonexistentUser";

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userService.findByUsername(username));
    }
}

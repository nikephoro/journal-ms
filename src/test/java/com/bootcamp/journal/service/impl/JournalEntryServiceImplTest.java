package com.bootcamp.journal.service.impl;

import com.bootcamp.journal.model.JournalEntry;
import com.bootcamp.journal.model.User;
import com.bootcamp.journal.dto.CreateEntryRequest;
import com.bootcamp.journal.dto.JournalEntryResponseDto;
import com.bootcamp.journal.repository.JournalEntryRepository;
import com.bootcamp.journal.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JournalEntryServiceImplTest {

    @Mock
    private JournalEntryRepository journalEntryRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private JournalEntryServiceImpl journalEntryService;

    @Test
    void findAllJournalEntriesByUsername_returnsEntries_whenUserExistsAndHasEntries() {
        String username = "testUser";
        User mockUser = new User();
        mockUser.setId(1);
        mockUser.setUsername(username);

        JournalEntry entry1 = new JournalEntry();
        entry1.setId(1);
        entry1.setContent("Entry 1");
        entry1.setUser(mockUser);

        JournalEntry entry2 = new JournalEntry();
        entry2.setId(2);
        entry2.setContent("Entry 2");
        entry2.setUser(mockUser);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(mockUser));
        when(journalEntryRepository.findAllByUser(mockUser)).thenReturn(List.of(entry1, entry2));

        List<JournalEntryResponseDto> result = journalEntryService.findAllJournalEntriesByUsername(username);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Entry 1", result.get(0).getContent());
        assertEquals("Entry 2", result.get(1).getContent());
    }

    @Test
    void findAllJournalEntriesByUsername_returnsEmptyList_whenUserExistsButHasNoEntries() {
        String username = "testUser";
        User mockUser = new User();
        mockUser.setId(1);
        mockUser.setUsername(username);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(mockUser));
        when(journalEntryRepository.findAllByUser(mockUser)).thenReturn(Collections.emptyList());

        List<JournalEntryResponseDto> result = journalEntryService.findAllJournalEntriesByUsername(username);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void findAllJournalEntriesByUsername_throwsException_whenUserDoesNotExist() {
        String username = "nonexistentUser";

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> journalEntryService.findAllJournalEntriesByUsername(username));
    }

    @Test
    void createEntry_returnsResponseDto_whenUserExists() {
        String username = "testUser";
        User mockUser = new User();
        mockUser.setId(1);
        mockUser.setUsername(username);

        CreateEntryRequest request = new CreateEntryRequest();
        request.setContent("New Entry");

        JournalEntry mockEntry = new JournalEntry();
        mockEntry.setId(1);
        mockEntry.setContent("New Entry");
        mockEntry.setUser(mockUser);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(mockUser));
        when(journalEntryRepository.save(any(JournalEntry.class))).thenReturn(mockEntry);

        JournalEntryResponseDto result = journalEntryService.createEntry(request, username);

        assertNotNull(result);
        assertEquals("New Entry", result.getContent());
        assertEquals(1, result.getId());
    }

    @Test
    void createEntry_throwsException_whenUserDoesNotExist() {
        String username = "nonexistentUser";
        CreateEntryRequest request = new CreateEntryRequest();
        request.setContent("New Entry");

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> journalEntryService.createEntry(request, username));
    }
}

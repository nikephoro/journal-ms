package com.bootcamp.journal.service.impl;

import com.bootcamp.journal.model.JournalEntry;
import com.bootcamp.journal.model.User;
import com.bootcamp.journal.dto.CreateEntryRequest;
import com.bootcamp.journal.dto.JournalEntryResponseDto;
import com.bootcamp.journal.repository.JournalEntryRepository;
import com.bootcamp.journal.repository.UserRepository;
import com.bootcamp.journal.service.JournalEntryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Implementation of the JournalEntryService interface.
 * Provides methods for managing journal entries, including retrieving and creating entries.
 */
@Service
@RequiredArgsConstructor
public class JournalEntryServiceImpl implements JournalEntryService {

    private final JournalEntryRepository journalEntryRepository;
    private final UserRepository userRepository;

    /**
     * Retrieves all journal entries for a given username.
     *
     * @param username the username of the user whose journal entries are to be retrieved
     * @return a list of JournalEntryResponseDto objects representing the user's journal entries
     * @throws RuntimeException if the user is not found
     */
    @Override
    public List<JournalEntryResponseDto> findAllJournalEntriesByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isPresent()) {
            List<JournalEntry> journalEntryList = journalEntryRepository.findAllByUser(user.get());
            if (journalEntryList.isEmpty()) {
                return Collections.emptyList(); // Return an empty list if no journal entries are found
            }
            return journalEntryList.stream()
                    .map(getJournalEntryJournalEntryResponseDtoFunction())
                    .collect(Collectors.toList());
        } else {
            throw new RuntimeException(); // Throw an exception if the user is not found
        }
    }

    /**
     * Creates a new journal entry for a given user.
     *
     * @param request  the CreateEntryRequest object containing the content of the new journal entry
     * @param username the username of the user creating the journal entry
     * @return a JournalEntryResponseDto object representing the created journal entry
     * @throws RuntimeException if the user is not found
     */
    @Override
    public JournalEntryResponseDto createEntry(CreateEntryRequest request, String username) {
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isPresent()) {
            JournalEntry journalEntry = JournalEntry
                    .builder()
                    .user(user.get())
                    .content(request.getContent())
                    .build();
            JournalEntry savedEntry = journalEntryRepository.save(journalEntry);
            return JournalEntryResponseDto
                    .builder()
                    .content(savedEntry.getContent())
                    .createdAt(savedEntry.getCreatedAt())
                    .userId(savedEntry.getId())
                    .id(savedEntry.getId())
                    .build();
        } else {
            throw new RuntimeException(); // Throw an exception if the user is not found
        }
    }

    /**
     * Helper method to convert a JournalEntry object to a JournalEntryResponseDto object.
     *
     * @return a Function that maps a JournalEntry to a JournalEntryResponseDto
     */
    private static Function<JournalEntry, JournalEntryResponseDto> getJournalEntryJournalEntryResponseDtoFunction() {
        return entry -> JournalEntryResponseDto
                .builder()
                .id(entry.getId())
                .userId(entry.getUser().getId())
                .createdAt(entry.getCreatedAt())
                .content(entry.getContent())
                .build();
    }
}
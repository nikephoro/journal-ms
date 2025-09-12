package com.bootcamp.journal.service.impl;

import com.bootcamp.journal.dao.JournalEntry;
import com.bootcamp.journal.dao.User;
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

@Service
@RequiredArgsConstructor
public class JournalEntryServiceImpl implements JournalEntryService {

    private final JournalEntryRepository journalEntryRepository;

    private final UserRepository userRepository;

    @Override
    public List<JournalEntryResponseDto> findAllJournalEntriesByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isPresent()) {
            List<JournalEntry> journalEntryList = journalEntryRepository.findAllByUser(user.get());
            if (journalEntryList.isEmpty()) {
                return Collections.emptyList(); // Fastest για empty lists
            }
            return journalEntryList.stream()
                    .map(getJournalEntryJournalEntryResponseDtoFunction())
                    .collect(Collectors.toList());
        } else {
            throw new RuntimeException();
        }

    }

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
         throw new RuntimeException();
        }
    }

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

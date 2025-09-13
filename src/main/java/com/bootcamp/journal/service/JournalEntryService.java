package com.bootcamp.journal.service;

import com.bootcamp.journal.dto.JournalEntryRequest;
import com.bootcamp.journal.dto.JournalEntryResponseDto;
import jakarta.validation.Valid;

import java.util.List;

public interface JournalEntryService {
    List<JournalEntryResponseDto> findAllJournalEntriesByUsername(String username);

    JournalEntryResponseDto createEntry(@Valid JournalEntryRequest request, String username);

    JournalEntryResponseDto updateJournalEntry(String id, JournalEntryRequest request, String username);

    void deleteJournalEntryById(String id);
}

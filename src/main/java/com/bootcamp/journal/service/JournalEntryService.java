package com.bootcamp.journal.service;

import com.bootcamp.journal.dto.CreateEntryRequest;
import com.bootcamp.journal.dto.JournalEntryResponseDto;
import jakarta.validation.Valid;

import java.util.List;

public interface JournalEntryService {
    List<JournalEntryResponseDto> findAllJournalEntriesByUsername(String username);

    JournalEntryResponseDto createEntry(@Valid CreateEntryRequest request, String username);

}

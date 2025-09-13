package com.bootcamp.journal.controller;

import com.bootcamp.journal.dto.JournalEntryRequest;
import com.bootcamp.journal.dto.JournalEntryResponseDto;
import com.bootcamp.journal.service.JournalEntryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/journal")
@RequiredArgsConstructor
public class JournalEntryController {

    private final JournalEntryService journalEntryService;

    @PostMapping("/entries")
    public ResponseEntity<JournalEntryResponseDto> createEntry(@Valid @RequestBody JournalEntryRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        JournalEntryResponseDto entry = journalEntryService.createEntry(request, username);
        return ResponseEntity.status(HttpStatus.CREATED).body(entry);
    }

    @GetMapping("/entries")
    public ResponseEntity<List<JournalEntryResponseDto>> getAllJournalEntries() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        List<JournalEntryResponseDto> journalEntryResponseDtoList = journalEntryService.findAllJournalEntriesByUsername(username);

        return ResponseEntity.ok(journalEntryResponseDtoList);
    }
    @PutMapping("/entries/{id}")
    public ResponseEntity<JournalEntryResponseDto> updateEntry(@PathVariable String id, @RequestBody JournalEntryRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        JournalEntryResponseDto updatedEntry = journalEntryService
                .updateJournalEntry(id, request,username);
        return ResponseEntity.ok(updatedEntry);
    }

    @DeleteMapping("/entries/{id}")
    public ResponseEntity<Void> deleteEntry(@PathVariable String id) {

        journalEntryService.deleteJournalEntryById(id);
        return ResponseEntity.noContent().build();
    }
}

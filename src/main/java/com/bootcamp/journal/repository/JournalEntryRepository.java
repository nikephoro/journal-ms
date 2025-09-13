package com.bootcamp.journal.repository;

import com.bootcamp.journal.model.JournalEntry;
import com.bootcamp.journal.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JournalEntryRepository extends JpaRepository<JournalEntry, Integer> {
    List<JournalEntry> findAllByUser(User user);
}

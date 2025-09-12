package com.bootcamp.journal.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class JournalEntryResponseDto {

    private Integer id;

    private Integer userId;

    private String content;

    private LocalDateTime createdAt;
}

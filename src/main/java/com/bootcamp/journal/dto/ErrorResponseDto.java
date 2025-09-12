package com.bootcamp.journal.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ErrorResponseDto extends ApiResponseDto<Void> {
    private String errorCode;
    private List<String> errors;

    public ErrorResponseDto(String status, String message, String errorCode, List<String> errors) {
        super(status, message, null);
        this.errorCode = errorCode;
        this.errors = errors;
    }
}

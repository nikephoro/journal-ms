package com.bootcamp.journal.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class UserDto {
    private Integer id;
    private String name;
    private String email;
}

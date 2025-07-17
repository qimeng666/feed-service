package com.example.feed_service.Dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NoteDto {
    private Long id;
    private Long authorId;
    private String title;
    private String content;
    private LocalDateTime createdTime;
}

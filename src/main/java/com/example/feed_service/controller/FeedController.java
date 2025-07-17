package com.example.feed_service.controller;

import com.example.feed_service.Dto.NoteDto;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/feeds")
public class FeedController {

    public List<NoteDto> getFeeds(Long userId) {
        return null;
    }
}

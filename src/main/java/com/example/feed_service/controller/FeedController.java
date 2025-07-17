package com.example.feed_service.controller;

import com.example.feed_service.Dto.FeedDto;
import com.example.feed_service.Dto.NoteDto;
import com.example.feed_service.service.FeedService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/feeds")
public class FeedController {
    private final FeedService feedService;

    public FeedController(FeedService feedService) {
        this.feedService = feedService;
    }

    @GetMapping("/{userId}")
    public List<FeedDto> getFeeds(@PathVariable Long userId,
                                  @RequestParam(defaultValue = "true") boolean withDetailed) {

        return feedService.getFeedForUser(userId, withDetailed);
    }
}

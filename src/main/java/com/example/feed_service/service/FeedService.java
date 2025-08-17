package com.example.feed_service.service;

import com.example.feed_service.Dto.FeedDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FeedService {
    public List<FeedDto> getFeedForUser(Long userId, boolean withDetailed);
    /**
     * 获取用户自己的笔记
     */
    List<FeedDto> getNotesForUser(Long userId);
}

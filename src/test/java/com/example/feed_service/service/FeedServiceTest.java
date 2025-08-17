package com.example.feed_service.service;

import com.example.feed_service.Dto.FeedDto;
import com.example.feed_service.service.impl.FeedServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class FeedServiceTest {

    @Autowired
    private FeedService feedService;

    @Test
    public void testGetNotesForUser_UserWithNoFollowing_ShouldReturnEmptyList() {
        // 测试用户无关注时返回空列表
        Long userId = 8L;
        
        List<FeedDto> result = feedService.getNotesForUser(userId);
        
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testGetNotesForUser_UserFollowingHasNoNotes_ShouldReturnEmptyList() {
        // 测试用户关注的人无笔记时返回空列表
        Long userId = 9L;
        
        List<FeedDto> result = feedService.getNotesForUser(userId);
        
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}

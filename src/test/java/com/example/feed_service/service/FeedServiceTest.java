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

    @Test
    public void testGetNotesForUser_UserFollowingHasNotes_ShouldReturnSortedResults() {
        // 测试用户关注的人有笔记时正确返回排序结果
        Long userId = 4L;
        
        List<FeedDto> result = feedService.getNotesForUser(userId);
        
        assertNotNull(result);
        assertFalse(result.isEmpty());
        
        // 验证按创建时间倒序排序
        for (int i = 0; i < result.size() - 1; i++) {
            assertTrue(result.get(i).getCreatedAt().isAfter(result.get(i + 1).getCreatedAt()) || 
                      result.get(i).getCreatedAt().isEqual(result.get(i + 1).getCreatedAt()));
        }
    }

    @Test
    public void testGetNotesForUser_UserFollowingMultipleUsers_ShouldAggregateNotesFromMultipleSources() {
        // 测试用户关注多人时正确聚合多来源笔记
        Long userId = 1L;
        
        List<FeedDto> result = feedService.getNotesForUser(userId);
        
        assertNotNull(result);
        assertFalse(result.isEmpty());
        
        // 验证聚合了多个作者的笔记
        var authorIds = result.stream()
                .map(feed -> feed.getNote().getUserId())
                .distinct()
                .toList();
        
        assertTrue(authorIds.size() > 1, "Should aggregate notes from multiple authors");
        
        // 验证按创建时间倒序排序
        for (int i = 0; i < result.size() - 1; i++) {
            assertTrue(result.get(i).getCreatedAt().isAfter(result.get(i + 1).getCreatedAt()) || 
                      result.get(i).getCreatedAt().isEqual(result.get(i + 1).getCreatedAt()));
        }
    }
}

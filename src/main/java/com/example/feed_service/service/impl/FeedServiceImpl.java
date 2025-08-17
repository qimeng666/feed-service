package com.example.feed_service.service.impl;

import com.example.feed_service.Dto.FeedDto;
import com.example.feed_service.Dto.NoteDto;
import com.example.feed_service.clinet.NoteClient;
import com.example.feed_service.clinet.UserClient;
import com.example.feed_service.entity.UserFeed;
import com.example.feed_service.repository.UserFeedRepository;
import com.example.feed_service.service.FeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FeedServiceImpl implements FeedService {
    
    @Autowired
    private NoteClient noteClient;

    @Autowired
    private UserClient userClient;
    
    @Autowired
    private UserFeedRepository repo;
    
    @Override
    public List<FeedDto> getFeedForUser(Long userId, boolean withDetailed) {
        List<UserFeed> feeds = repo.findByUserIdOrderByCreatedTimeDesc(userId);
        if (feeds.isEmpty()) {
            return Collections.emptyList();
        }
        if(!withDetailed){
            return feeds.stream()
                    .map(f -> new FeedDto(
                            new NoteDto(f.getNoteId(), null, null, null), // 只有 ID
                            f.getCreatedTime()
                            ))
                    .collect(Collectors.toList());
        }
        List<Long> noteIds = feeds.stream()
                .map(UserFeed::getNoteId)
                .distinct()
                .collect(Collectors.toList());
        List<NoteDto> notes = noteClient.getNotesByIds(noteIds);
        Map<Long, NoteDto> noteMap = notes.stream()
                .collect(Collectors.toMap(NoteDto::getId, n -> n));
        return feeds.stream()
                .map(f -> new FeedDto(
                        noteMap.get(f.getNoteId()), // 使用映射获取详细信息
                        f.getCreatedTime()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<FeedDto> getNotesForUser(Long userId) {
        // 1. 查询用户关注列表
        List<Long> followingIds = userClient.getFollowing(userId);
        if (followingIds == null || followingIds.isEmpty()) {
            return Collections.emptyList();
        }
        
        // 2. 根据关注用户ID批量查询最新笔记
        List<NoteDto> allNotes = new ArrayList<>();
        for (Long authorId : followingIds) {
            try {
                List<NoteDto> notes = noteClient.getNotesByAuthorIds(authorId);
                if (notes != null && !notes.isEmpty()) {
                    allNotes.addAll(notes);
                } else {
                    System.err.println("No notes returned for author " + authorId);
                }
            } catch (Exception e) {
                // 记录错误但继续处理其他用户
                System.err.println("Failed to get notes for author " + authorId + ": " + e.getMessage());
                e.printStackTrace();
            }
        }
        
        if (allNotes.isEmpty()) {
            return Collections.emptyList();
        }
        
        // 3. 按发布时间倒序聚合排序返回结果
        return allNotes.stream()
                .sorted(Comparator.comparing(NoteDto::getCreatedTime, Comparator.nullsLast(Comparator.reverseOrder())))
                .map(note -> new FeedDto(note, note.getCreatedTime()))
                .collect(Collectors.toList());
    }
}

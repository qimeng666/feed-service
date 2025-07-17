package com.example.feed_service.service.impl;

import com.example.feed_service.Dto.FeedDto;
import com.example.feed_service.Dto.NoteDto;
import com.example.feed_service.clinet.NoteClient;
import com.example.feed_service.entity.UserFeed;
import com.example.feed_service.repository.UserFeedRepository;
import com.example.feed_service.service.FeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FeedServiceImpl implements FeedService {
    
    @Autowired
    private NoteClient noteClient;
    
    @Autowired
    private UserFeedRepository repo;
    
    @Override
    public List<FeedDto> getFeedForUser(Long userId, boolean withDetailed) {
        List<UserFeed> feeds = repo.findByUserIdOrderByCreatedTimeDesc(userId);
        if (feeds.isEmpty()) {
            return Collections.emptyList();
        }
        System.out.println(withDetailed);
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
}

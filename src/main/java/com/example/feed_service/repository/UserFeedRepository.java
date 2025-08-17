package com.example.feed_service.repository;

import com.example.feed_service.entity.UserFeed;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserFeedRepository extends JpaRepository<UserFeed, Long> {
    List<UserFeed> findByUserIdOrderByCreatedTimeDesc(Long userId);
    
    List<UserFeed> findByNoteIdIn(List<Long> noteIds);
}

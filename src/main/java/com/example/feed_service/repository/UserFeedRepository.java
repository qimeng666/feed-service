package com.example.feed_service.repository;

import com.example.feed_service.entity.UserFeed;
import org.springframework.data.jpa.repository.JpaRepository;

import java.beans.JavaBean;
import java.util.List;

public interface UserFeedRepository extends JpaRepository<UserFeed, Long> {
    List<UserFeed> findByUserIdOrderByCreatedAtDesc(Long userId);
}

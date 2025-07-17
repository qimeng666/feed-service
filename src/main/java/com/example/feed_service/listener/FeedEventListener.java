package com.example.feed_service.listener;

import com.example.feed_service.Dto.NoteEvent;
import com.example.feed_service.clinet.UserClient;
import com.example.feed_service.entity.UserFeed;
import com.example.feed_service.repository.UserFeedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FeedEventListener {

    @Autowired
    private UserClient userClient;
    @Autowired
    private UserFeedRepository feedRepo;

    @KafkaListener(topics  = "note-creation-event", containerFactory = "kafkaListenerContainerFactory")
    public void handleNoteCreatedEvent(NoteEvent event) {
        List<Long> followers = userClient.getFollowers(event.getUserId());
        if(followers == null || followers.isEmpty()) {
            return;
        }
        List<UserFeed> feeds = followers.stream()
                .map(uid -> {
                    UserFeed uf = new UserFeed();
                    uf.setUserId(uid);
                    uf.setNoteId(event.getNoteId());
                    uf.setCreatedTime(LocalDateTime.now());
                    return uf;
                }).collect(Collectors.toList());
        feedRepo.saveAll(feeds);
    }
}

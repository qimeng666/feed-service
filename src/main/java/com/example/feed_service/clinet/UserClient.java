package com.example.feed_service.clinet;

import com.example.feed_service.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(
        name = "user-service",
        url = "${USER_SERVICE_URL:http://localhost:8081}",
        configuration = FeignConfig.class
)
public interface UserClient {
    @GetMapping("/internal/users/{userId}/followers")
    List<Long> getFollowers(@PathVariable("userId") Long userId);
}

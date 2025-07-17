package com.example.feed_service.clinet;

import com.example.feed_service.Dto.NoteDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "note-service", url = "${NOTE_SERVICE_URL:http://localhost:8080}")
public interface NoteClient {
    @GetMapping("/internal/notes/batch")
    List<NoteDto> getNotesByIds(@RequestParam("ids") List<Long> noteIds);
}

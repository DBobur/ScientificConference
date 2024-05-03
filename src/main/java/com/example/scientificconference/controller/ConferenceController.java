package com.example.scientificconference.controller;

import com.example.scientificconference.dto.ConferenceDto;
import com.example.scientificconference.dto.RegisterDto;
import com.example.scientificconference.entity.Conference;
import com.example.scientificconference.service.ConferenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/conference")
public class ConferenceController {
    private final ConferenceService conferenceService;

    @PostMapping("/add")
    public ResponseEntity<Conference> add(
            @RequestBody ConferenceDto conferenceDto
            ){
        return ResponseEntity.ok(conferenceService.add(conferenceDto));
    }

    @PostMapping("/update")
    public ResponseEntity<Conference> update(
            @RequestParam UUID id,
            @RequestBody ConferenceDto updateDto
            ){
        return ResponseEntity.ok(conferenceService.update(id,updateDto));
    }
    @DeleteMapping("/delete")
    public ResponseEntity<String> delete(
            @RequestParam UUID id
            //@PathVariable String id
    ){
        conferenceService.delete(id);
        return ResponseEntity.ok("Item with ID " + id + " deleted successfully");
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<Conference>> getAll() {
        return ResponseEntity.ok(conferenceService.getAll());
    }
}

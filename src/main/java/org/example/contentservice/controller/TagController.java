package org.example.contentservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.contentservice.model.Tag;
import org.example.contentservice.repository.TagRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagRepository tagRepository;

    @GetMapping
    public ResponseEntity<List<String>> getAllTags() {
        return ResponseEntity.ok(tagRepository.findAll().stream()
                .map(Tag::getName)
                .collect(Collectors.toList()));
    }
}

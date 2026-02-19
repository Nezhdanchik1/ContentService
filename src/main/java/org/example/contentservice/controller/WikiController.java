package org.example.contentservice.controller;

import org.example.contentservice.dto.WikiEntryDTO;
import org.example.contentservice.mapper.WikiMapper;
import org.example.contentservice.model.WikiEntry;
import org.example.contentservice.service.WikiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/wiki")
@RequiredArgsConstructor
public class WikiController {

    private final WikiService wikiService;
    private final WikiMapper wikiMapper;

    @PostMapping("/from-article/{articleId}")
    public ResponseEntity<WikiEntryDTO> createFromArticle(@PathVariable Long articleId) {
        WikiEntry wiki = wikiService.createFromArticle(articleId);
        return ResponseEntity.ok(wikiMapper.toDTO(wiki));
    }

    @GetMapping("/room/{roomId}")
    public ResponseEntity<List<WikiEntryDTO>> getByRoom(@PathVariable Long roomId) {
        List<WikiEntryDTO> dtos = wikiService.getByRoom(roomId)
                .stream()
                .map(wikiMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
}

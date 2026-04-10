package org.example.contentservice.controller;

import org.example.contentservice.dto.WikiEntryDTO;
import org.example.contentservice.dto.WikiSectionDTO;
import org.example.contentservice.mapper.WikiMapper;
import org.example.contentservice.model.WikiEntry;
import org.example.contentservice.model.WikiSection;
import org.example.contentservice.service.WikiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import org.example.contentservice.dto.response.ArticlePreviewDto;
import org.example.contentservice.dto.response.WikiLandingResponse;
import org.example.contentservice.model.InteractionType;
@RestController
@RequestMapping("/api/wiki")
@RequiredArgsConstructor
public class WikiController {

    private final WikiService wikiService;
    private final WikiMapper wikiMapper;

    @PostMapping("/from-article/{articleId}")
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<WikiEntryDTO> createFromArticle(
            @PathVariable Long articleId,
            @RequestParam(required = false) Long sectionId) {
        WikiEntry wiki = wikiService.createFromArticle(articleId, sectionId);
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

    @PostMapping("/sections")
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<WikiSectionDTO> createSection(
            @RequestParam Long roomId,
            @RequestParam String name) {
        WikiSection section = wikiService.createSection(roomId, name);
        return ResponseEntity.ok(wikiMapper.toSectionDTO(section));
    }

    @GetMapping("/room/{roomId}/sections")
    public ResponseEntity<List<WikiSectionDTO>> getSectionsByRoom(@PathVariable Long roomId) {
        List<WikiSectionDTO> dtos = wikiService.getSectionsByRoom(roomId)
                .stream()
                .map(wikiMapper::toSectionDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/landing")
    public ResponseEntity<WikiLandingResponse> getLandingPage(@RequestParam(required = false) List<Long> roomIds) {
        return ResponseEntity.ok(wikiService.getLandingPage(roomIds));
    }

    @GetMapping("/search")
    public ResponseEntity<List<ArticlePreviewDto>> searchArticles(
            @RequestParam String q,
            @RequestParam(required = false) List<Long> roomIds) {
        return ResponseEntity.ok(wikiService.searchArticles(q, roomIds));
    }

    @PostMapping("/interactions/{articleId}/view")
    public ResponseEntity<Void> recordView(
            @PathVariable Long articleId,
            @RequestParam(required = false) Long userId) {
        wikiService.recordInteraction(articleId, userId, InteractionType.VIEW);
        return ResponseEntity.ok().build();
    }
}

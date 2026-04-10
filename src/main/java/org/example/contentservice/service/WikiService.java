package org.example.contentservice.service;

import org.example.contentservice.model.WikiEntry;
import org.example.contentservice.model.WikiSection;

import java.util.List;

public interface WikiService {

    WikiEntry createFromArticle(Long articleId, Long sectionId);

    List<WikiEntry> getByRoom(Long roomId);

    WikiSection createSection(Long roomId, String name);

    List<WikiSection> getSectionsByRoom(Long roomId);

    org.example.contentservice.dto.response.WikiLandingResponse getLandingPage(List<Long> roomIds);

    List<org.example.contentservice.dto.response.ArticlePreviewDto> searchArticles(String query, List<Long> roomIds);

    void recordInteraction(Long articleId, Long userId, org.example.contentservice.model.InteractionType type);
}

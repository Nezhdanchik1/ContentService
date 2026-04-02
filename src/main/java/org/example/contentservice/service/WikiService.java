package org.example.contentservice.service;

import org.example.contentservice.model.WikiEntry;
import org.example.contentservice.model.WikiSection;

import java.util.List;

public interface WikiService {

    WikiEntry createFromArticle(Long articleId, Long sectionId);

    List<WikiEntry> getByRoom(Long roomId);

    WikiSection createSection(Long roomId, String name);

    List<WikiSection> getSectionsByRoom(Long roomId);
}

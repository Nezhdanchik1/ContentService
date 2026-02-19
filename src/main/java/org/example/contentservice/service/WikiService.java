package org.example.contentservice.service;

import org.example.contentservice.model.WikiEntry;

import java.util.List;

public interface WikiService {

    WikiEntry createFromArticle(Long articleId);

    List<WikiEntry> getByRoom(Long roomId);
}

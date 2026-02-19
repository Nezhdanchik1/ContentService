package org.example.contentservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.contentservice.model.Article;
import org.example.contentservice.model.WikiEntry;
import org.example.contentservice.repository.ArticleRepository;
import org.example.contentservice.repository.WikiEntryRepository;
import org.example.contentservice.service.WikiService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class WikiServiceImpl implements WikiService {

    private final WikiEntryRepository wikiRepository;
    private final ArticleRepository articleRepository;

    @Override
    public WikiEntry createFromArticle(Long articleId) {

        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new RuntimeException("Article not found"));

        WikiEntry wiki = WikiEntry.builder()
                .roomId(article.getRoomId())
                .sourceArticle(article)
                .title(article.getTitle())
                .content(article.getContent())
                .build();

        return wikiRepository.save(wiki);
    }

    @Override
    @Transactional(readOnly = true)
    public List<WikiEntry> getByRoom(Long roomId) {
        return wikiRepository.findByRoomId(roomId);
    }
}

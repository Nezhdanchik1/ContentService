package org.example.contentservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.contentservice.model.Article;
import org.example.contentservice.model.WikiEntry;
import org.example.contentservice.model.WikiSection;
import org.example.contentservice.repository.ArticleRepository;
import org.example.contentservice.repository.WikiEntryRepository;
import org.example.contentservice.repository.WikiSectionRepository;
import org.example.contentservice.service.WikiService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;
import java.time.LocalDateTime;

import lombok.extern.slf4j.Slf4j;
import org.example.contentservice.dto.response.ArticlePreviewDto;
import org.example.contentservice.dto.response.CategoryStatDto;
import org.example.contentservice.dto.response.WikiLandingResponse;
import org.example.contentservice.model.ArticleInteraction;
import org.example.contentservice.model.InteractionType;
import org.example.contentservice.model.Tag;
import org.example.contentservice.repository.ArticleInteractionRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class WikiServiceImpl implements WikiService {

    private final WikiEntryRepository wikiRepository;
    private final WikiSectionRepository sectionRepository;
    private final ArticleRepository articleRepository;
    private final ArticleInteractionRepository interactionRepository;

    @Override
    public WikiEntry createFromArticle(Long articleId, Long sectionId) {

        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new RuntimeException("Article not found"));

        WikiSection section = null;
        if (sectionId != null) {
            section = sectionRepository.findById(sectionId)
                    .orElseThrow(() -> new RuntimeException("Section not found"));
        }

        WikiEntry wiki = WikiEntry.builder()
                .roomId(article.getRoomId())
                .section(section)
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

    @Override
    public WikiSection createSection(Long roomId, String name) {
        WikiSection section = WikiSection.builder()
                .roomId(roomId)
                .name(name)
                .build();
        return sectionRepository.save(section);
    }

    @Override
    @Transactional(readOnly = true)
    public List<WikiSection> getSectionsByRoom(Long roomId) {
        return sectionRepository.findByRoomId(roomId);
    }

    @Override
    @Cacheable(value = "wiki_landing", key="'landing_' + (#roomIds != null ? #roomIds.hashCode() : 'all')")
    @Transactional(readOnly = true)
    public WikiLandingResponse getLandingPage(List<Long> roomIds) {
        // 1. Categories (Top 10 largest)
        List<CategoryStatDto> categories;
        if (roomIds == null || roomIds.isEmpty()) {
            categories = articleRepository.countArticlesByTag(PageRequest.of(0, 10));
        } else {
            categories = articleRepository.countArticlesByTagAndRoomIds(roomIds, PageRequest.of(0, 10));
        }

        // 2. Popular
        LocalDateTime sevenDaysAgo = LocalDateTime.now().minusDays(7);
        List<Long> popularIds;
        if (roomIds == null || roomIds.isEmpty()) {
            popularIds = interactionRepository.findPopularArticleIdsSince(sevenDaysAgo, PageRequest.of(0, 5));
        } else {
            popularIds = interactionRepository.findPopularArticleIdsByRoomIdsSince(sevenDaysAgo, roomIds, PageRequest.of(0, 5));
        }

        List<Article> popularArticles = popularIds.isEmpty()
                ? Collections.emptyList()
                : articleRepository.findByIdIn(popularIds);

        List<ArticlePreviewDto> popularDtos = popularArticles.stream()
                .map(this::mapToPreview)
                .collect(Collectors.toList());

        // 3. Recommended (Article of the Week by AI Score)
        Article recommended;
        if (roomIds == null || roomIds.isEmpty()) {
            recommended = articleRepository.findFirstByOrderByAiScoreDesc();
        } else {
            recommended = articleRepository.findFirstByRoomIdInOrderByAiScoreDesc(roomIds);
        }
        ArticlePreviewDto recommendedDto = recommended != null ? mapToPreview(recommended) : null;

        return WikiLandingResponse.builder()
                .categories(categories)
                .popular(popularDtos)
                .recommended(recommendedDto)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ArticlePreviewDto> searchArticles(String query, List<Long> roomIds) {
        if (query == null || query.trim().isEmpty()) {
            return Collections.emptyList();
        }

        List<Article> results;
        if (roomIds == null || roomIds.isEmpty()) {
            results = articleRepository.searchByTitleOrContent(query);
        } else {
            results = articleRepository.searchByTitleOrContentAndRoomIds(query, roomIds);
        }
        
        // Put title matches first
        List<ArticlePreviewDto> dtos = new ArrayList<>();
        String lowerQuery = query.toLowerCase();

        for (Article a : results) {
            if (a.getTitle().toLowerCase().contains(lowerQuery)) {
                dtos.add(0, mapToPreview(a));
            } else {
                dtos.add(mapToPreview(a));
            }
        }

        return dtos;
    }

    @Override
    @Transactional
    public void recordInteraction(Long articleId, Long userId, InteractionType type) {
        ArticleInteraction interaction = ArticleInteraction.builder()
                .articleId(articleId)
                .userId(userId)
                .interactionType(type)
                .build();
        interactionRepository.save(interaction);
        log.info("Recorded {} for article {}", type, articleId);
    }

    private ArticlePreviewDto mapToPreview(Article article) {
        String previewText = article.getContent();
        if (previewText != null && previewText.length() > 150) {
            previewText = previewText.substring(0, 150) + "...";
        }
        
        Long views = interactionRepository.countByArticleIdAndInteractionType(article.getId(), InteractionType.VIEW);
        
        return ArticlePreviewDto.builder()
                .id(article.getId())
                .title(article.getTitle())
                .previewText(previewText)
                .aiScore(article.getAiScore() != null ? article.getAiScore() : 0.0)
                .tags(article.getTags() != null
                        ? article.getTags().stream().map(Tag::getName).collect(Collectors.toSet())
                        : Collections.emptySet())
                .viewCount(views != null ? views : 0L)
                .build();
    }
}

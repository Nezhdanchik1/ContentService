package org.example.contentservice.dto;

import org.example.contentservice.model.DifficultyLevel;
import org.example.contentservice.model.AIStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArticleDTO {

    private Long id;
    private Long userId;
    private Long roomId;
    private String title;
    private String content;
    private DifficultyLevel difficultyLevel;
    private Double aiScore;
    private AIStatus aiReviewStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private List<WikiEntryDTO> wikiEntries; // вложенные Wiki
}

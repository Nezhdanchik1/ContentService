package org.example.contentservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.contentservice.dto.WikiEntryDTO;
import org.example.contentservice.model.AIStatus;
import org.example.contentservice.model.DifficultyLevel;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateArticleRequest {
    private Long userId;
    private Long roomId;
    private String title;
    private String content;
    private DifficultyLevel difficultyLevel;
}

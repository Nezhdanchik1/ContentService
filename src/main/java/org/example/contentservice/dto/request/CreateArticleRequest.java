package org.example.contentservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.contentservice.model.DifficultyLevel;

import java.util.Set;

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
    private Set<String> tags;
}

package org.example.contentservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticlePreviewDto {
    private Long id;
    private String title;
    private String previewText;
    private Double aiScore;
    private Set<String> tags;
    private Long viewCount;
    // Можно добавить likes, если понадобится
}

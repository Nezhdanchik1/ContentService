package org.example.contentservice.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserActionEvent {
    private String eventId;
    private Long userId;
    private String type; // Например: ARTICLE_READ, ARTICLE_PUBLISHED
    private Long targetId;
    private Long directionId;
}

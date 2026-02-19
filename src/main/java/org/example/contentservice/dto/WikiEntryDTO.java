package org.example.contentservice.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WikiEntryDTO {

    private Long id;
    private Long roomId;
    private Long sourceArticleId;
    private String title;
    private String content;
    private LocalDateTime createdAt;
}

package org.example.contentservice.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDTO {

    private Long id;
    private Long userId;
    private String content;
    private Boolean isAccepted;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

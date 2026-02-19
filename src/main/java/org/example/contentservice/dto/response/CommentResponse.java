package org.example.contentservice.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentResponse {

    private Long id;
    private Long userId;
    private Long postId;
    private String content;
    private boolean isAccepted;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

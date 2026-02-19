package org.example.contentservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.contentservice.model.AIStatus;
import org.example.contentservice.model.PostType;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OnePostResponse {

    private Long id;
    private Long userId;
    private Long roomId;
    private String title;
    private String content;
    private PostType postType;
    private AIStatus aiStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private List<CommentResponse> comments; // вложенные DTO комментариев
}

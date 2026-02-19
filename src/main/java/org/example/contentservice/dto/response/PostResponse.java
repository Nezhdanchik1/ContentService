package org.example.contentservice.dto.response;

import org.example.contentservice.model.PostType;
import org.example.contentservice.model.AIStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostResponse {

    private Long id;
    private Long userId;
    private Long roomId;
    private String title;
    private String content;
    private PostType postType;
    private AIStatus aiStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}

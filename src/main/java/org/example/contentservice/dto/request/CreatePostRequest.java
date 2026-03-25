package org.example.contentservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.contentservice.model.PostType;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreatePostRequest {

    private Long userId;
    private Long roomId;
    private String title;
    private String content;
    private PostType postType;
    private Set<String> tags;
}

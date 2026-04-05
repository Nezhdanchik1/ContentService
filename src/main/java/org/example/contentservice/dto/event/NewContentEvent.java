package org.example.contentservice.dto.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewContentEvent {
    private Long id;
    private String title;
    private Long userId;
    private Long roomId;
    private String contentType; // e.g., "POST", "ARTICLE"
    private LocalDateTime createdAt;
}

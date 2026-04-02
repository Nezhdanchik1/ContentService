package org.example.contentservice.dto;

import lombok.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WikiSectionDTO {
    private Long id;
    private Long roomId;
    private String name;
    private Integer orderIndex;
    private List<WikiEntryDTO> entries;
}

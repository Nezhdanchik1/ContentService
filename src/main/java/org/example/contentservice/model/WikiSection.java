package org.example.contentservice.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "wiki_sections")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WikiSection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "room_id", nullable = false)
    private Long roomId;

    @Column(nullable = false)
    private String name;

    @Column(name = "order_index")
    private Integer orderIndex;

    @OneToMany(mappedBy = "section", cascade = CascadeType.ALL)
    private List<WikiEntry> entries;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}

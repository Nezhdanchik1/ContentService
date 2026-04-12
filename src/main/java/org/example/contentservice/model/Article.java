package org.example.contentservice.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "articles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ID из User Service
    @Column(name = "user_id", nullable = false)
    private Long userId;

    // ID из Room Service
    @Column(name = "room_id", nullable = false)
    private Long roomId;

    @Column(name = "direction_id")
    private Long directionId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "difficulty_level", nullable = false)
    private DifficultyLevel difficultyLevel;

    @Column(name = "ai_score")
    private Double aiScore;

    @Enumerated(EnumType.STRING)
    @Column(name = "ai_review_status", nullable = false)
    private AIStatus aiReviewStatus = AIStatus.PENDING;

    @OneToMany(mappedBy = "sourceArticle", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WikiEntry> wikiEntries;

    @ManyToMany
    @JoinTable(
            name = "article_tags",
            joinColumns = @JoinColumn(name = "article_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}

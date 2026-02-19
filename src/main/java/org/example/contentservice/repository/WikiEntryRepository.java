package org.example.contentservice.repository;

import org.example.contentservice.model.WikiEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WikiEntryRepository extends JpaRepository<WikiEntry, Long> {

    List<WikiEntry> findByRoomId(Long roomId);

    List<WikiEntry> findBySourceArticle_Id(Long articleId);
}

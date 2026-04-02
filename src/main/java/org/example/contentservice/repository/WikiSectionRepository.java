package org.example.contentservice.repository;

import org.example.contentservice.model.WikiSection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface WikiSectionRepository extends JpaRepository<WikiSection, Long> {
    List<WikiSection> findByRoomId(Long roomId);
}

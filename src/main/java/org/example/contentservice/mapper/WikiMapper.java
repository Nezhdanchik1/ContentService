package org.example.contentservice.mapper;

import org.example.contentservice.dto.WikiEntryDTO;
import org.example.contentservice.dto.WikiSectionDTO;
import org.example.contentservice.model.WikiEntry;
import org.example.contentservice.model.WikiSection;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WikiMapper {

    @Mapping(source = "sourceArticle.id", target = "sourceArticleId")
    @Mapping(source = "section.id", target = "sectionId")
    WikiEntryDTO toDTO(WikiEntry wikiEntry);

    WikiEntry toEntity(WikiEntryDTO dto);

    WikiSectionDTO toSectionDTO(WikiSection section);

    WikiSection toSectionEntity(WikiSectionDTO dto);
}

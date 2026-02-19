package org.example.contentservice.mapper;

import org.example.contentservice.dto.WikiEntryDTO;
import org.example.contentservice.model.WikiEntry;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WikiMapper {

    @Mapping(source = "sourceArticle.id", target = "sourceArticleId")
    WikiEntryDTO toDTO(WikiEntry wikiEntry);

    WikiEntry toEntity(WikiEntryDTO dto);
}

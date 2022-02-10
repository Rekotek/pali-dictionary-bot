package com.scriptorium.pali.entity.mapper;

import com.scriptorium.pali.entity.WordDescription;
import com.scriptorium.pali.entity.dto.WordDescriptionDto;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface WordDescriptionMapper {
    WordDescription wordDtoToWordInDb(WordDescriptionDto wordDescriptionDto);

    WordDescriptionDto wordInDbToWordDto(WordDescription wordDescription);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateWordInDbFromWordDto(WordDescriptionDto wordDescriptionDto, @MappingTarget WordDescription wordDescription);

    List<WordDescriptionDto> mapFromDb(List<WordDescription> words);
}

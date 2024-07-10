package org.example.mappers;

import org.example.DTO.ConsultationsDto;
import org.example.models.Consultations;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper
public interface ConsultationMapper {

    ConsultationMapper INSTANCE = Mappers.getMapper(ConsultationMapper.class);

    ConsultationsDto toConDto(Consultations consultations);

    Consultations toModel(ConsultationsDto dto);
}




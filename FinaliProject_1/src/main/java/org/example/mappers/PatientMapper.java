package org.example.mappers;

import org.example.DTO.PatientDto;
import org.example.models.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
@Mapper
public interface PatientMapper {


    PatientMapper INSTANCE = Mappers.getMapper(PatientMapper.class);

    PatientDto toPatDto(Patient patient);

    Patient toModel(PatientDto dto);
}

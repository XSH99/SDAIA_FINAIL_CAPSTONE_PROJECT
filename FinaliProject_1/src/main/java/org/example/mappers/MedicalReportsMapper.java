package org.example.mappers;

import org.example.DTO.MedicalReportsDto;
import org.example.models.MedicalReports;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MedicalReportsMapper {

    MedicalReportsMapper INSTANCE = Mappers.getMapper(MedicalReportsMapper.class);

    MedicalReportsDto toMedDto(MedicalReports medicalReports);

    MedicalReports toModel(MedicalReportsDto dto);
}





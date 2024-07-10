package org.example.mappers;

import org.example.DTO.DoctorDto;
import org.example.models.Doctor;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;



@Mapper
public interface DoctorMapper {

    DoctorMapper INSTANCE = Mappers.getMapper(DoctorMapper.class);

    DoctorDto toDocDto(Doctor doctor);

    Doctor toModel(DoctorDto dto);
}

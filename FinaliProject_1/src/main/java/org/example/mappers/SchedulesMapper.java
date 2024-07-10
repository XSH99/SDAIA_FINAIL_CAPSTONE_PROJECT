package org.example.mappers;

import org.example.DTO.SchedulesDto;
import org.example.models.Schedules;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper
public interface SchedulesMapper {

    SchedulesMapper INSTANCE = Mappers.getMapper(SchedulesMapper.class);

    SchedulesDto toSchDto(Schedules schedules);

    Schedules toModel(SchedulesDto dto);
}

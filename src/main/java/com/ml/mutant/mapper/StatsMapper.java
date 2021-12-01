package com.ml.mutant.mapper;

import com.ml.mutant.domain.Stats;
import com.ml.mutant.dto.StatsDto;
import org.mapstruct.Mapper;

@Mapper
public interface StatsMapper {

    StatsDto createDtoFromEntity(Stats stats);

}

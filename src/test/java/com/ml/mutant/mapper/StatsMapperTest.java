package com.ml.mutant.mapper;

import com.ml.mutant.domain.Stats;
import com.ml.mutant.dto.StatsDto;
import org.junit.Before;
import org.junit.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.Assert.assertEquals;

public class StatsMapperTest {

    private static StatsMapper statsMapper;

    @Before
    public void setup() {
        statsMapper = Mappers.getMapper(StatsMapper.class);
    }

    @Test
    public void test_createDtoFromEntity() {
        Stats stats = new Stats();
        stats.setCountHumanDna(100L);
        stats.setCountMutantDna(40L);

        StatsDto statsDto = statsMapper.createDtoFromEntity(stats);

        assertEquals(stats.getCountHumanDna(), statsDto.getCountHumanDna());
        assertEquals(stats.getCountMutantDna(), statsDto.getCountMutantDna());
        assertEquals(0.4D, statsDto.getRatio(), 0.0);
    }

}

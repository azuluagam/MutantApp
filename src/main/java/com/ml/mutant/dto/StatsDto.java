package com.ml.mutant.dto;

import lombok.Data;

@Data
public class StatsDto {

    private int countMutantDna;

    private int countHumanDna;

    private double ratio;
}

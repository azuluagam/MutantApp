package com.ml.mutant.domain;

import lombok.Data;

@Data
public class Stats {

    private long countMutantDna;

    private long countHumanDna;

    public double getRatio () {
        return (double) countMutantDna/countHumanDna;
    }

}

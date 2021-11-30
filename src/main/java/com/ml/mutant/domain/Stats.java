package com.ml.mutant.domain;

import lombok.Data;

@Data
public class Stats {

    private int countMutantDna;

    private int countHumanDna;

    public double getRatio () {
        return (double) countMutantDna/countHumanDna;
    }

}

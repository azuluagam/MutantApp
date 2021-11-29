package com.ml.mutant.dto;

import lombok.Data;

@Data
public class DnaDto {

    private String[] dna;

    public String[] getDna() {
        return dna;
    }

    public void setDna(String[] dna) {
        this.dna = dna;
    }
}

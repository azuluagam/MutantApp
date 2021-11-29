package com.ml.mutant.service;

import com.ml.mutant.domain.Mutant;

public interface MutantService {

    boolean isMutant(String[] dna);

    Mutant existsMutantByDna(String[] dna);
}

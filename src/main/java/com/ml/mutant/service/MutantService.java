package com.ml.mutant.service;

import com.ml.mutant.domain.Mutant;
import com.ml.mutant.domain.Stats;

public interface MutantService {

    boolean isMutant(String[] dna);

    Mutant existsMutantByDna(String[] dna);

    Stats getMutantStats();

}

package com.ml.mutant.service;

import com.ml.mutant.domain.Mutant;
import com.ml.mutant.domain.Stats;

import java.util.Optional;

public interface MutantService {

    boolean isMutant(String[] dna);

    Optional<Mutant> existsMutantByDna(String[] dna);

    Stats getMutantStats();

}

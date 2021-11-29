package com.ml.mutant.service.impl;

import com.ml.mutant.domain.Mutant;
import com.ml.mutant.repository.MutantRepository;
import com.ml.mutant.service.MutantService;
import com.ml.mutant.helper.MutantHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MutantServiceImpl implements MutantService {

    private final MutantRepository mutantRepository;

    @Autowired
    public MutantServiceImpl(MutantRepository mutantRepository) {
        this.mutantRepository = mutantRepository;
    }

    @Override
    public boolean isMutant(String[] dna) {
        Mutant mutant = existsMutantByDna(dna);
        if (mutant != null) {
            return mutant.isMutant();
        }
        char[][] dnaMatrix = MutantHelper.createDnaMatrix(dna);
        boolean isMutant = isMutant(dnaMatrix);
        mutant = new Mutant();
        mutant.setMutant(isMutant);
        mutant.setDna(dna);
        mutantRepository.save(mutant);
        return isMutant;
    }

    @Override
    public Mutant existsMutantByDna(String[] dna) {
        return mutantRepository.findByDna(dna);
    }

    private boolean isMutant(char[][] dnaMatrix) {
        int repeatedCount = 0;

        for (int i = 0; i < dnaMatrix.length; i++) {
            for(int j = 0; j < dnaMatrix.length; j++) {

                if (MutantHelper.checkHorizontal(i, j, dnaMatrix)) {
                    repeatedCount++;
                }
                if (MutantHelper.checkVertical(i, j, dnaMatrix)) {
                    repeatedCount++;
                }
                if (MutantHelper.checkForwardDiagonal(i, j, dnaMatrix)) {
                    repeatedCount++;
                }
                if (MutantHelper.checkBackwardDiagonal(i, j, dnaMatrix)) {
                    repeatedCount++;
                }
                if (repeatedCount > 1) {
                    return true;
                }
            }
        }

        return false;
    }

}

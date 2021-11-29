package com.ml.mutant.repository;

import com.ml.mutant.domain.Mutant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MutantRepository extends JpaRepository<Mutant, Integer> {

    Mutant findByDna(String[] dna);

}

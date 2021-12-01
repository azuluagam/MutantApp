package com.ml.mutant.repository;

import com.ml.mutant.domain.Mutant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MutantRepository extends JpaRepository<Mutant, Integer> {

    Optional<Mutant> findByDna(String[] dna);

    long countByIsMutant(boolean isMutant);

}

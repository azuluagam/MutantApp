package com.ml.mutant.repository;

import com.ml.mutant.domain.Mutant;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MutantAppDbTestConfig.class)
@TestPropertySource(locations = "classpath:application-test.properties")
public class MutantRepositoryTest {

    @Autowired
    private MutantRepository mutantRepository;

    private Mutant mutant;

    private Mutant human;

    @Before
    public void setUp() {
        String[] mutantDnaArray = {"TGGAC", "AAGGTT"};
        mutant = new Mutant();
        mutant.setMutant(true);
        mutant.setDna(mutantDnaArray);

        String[] humanDnaArray = {"TGATC", "CAGGTT"};
        human = new Mutant();
        human.setMutant(false);
        human.setDna(humanDnaArray);

        mutant = mutantRepository.save(mutant);
        human = mutantRepository.save(human);
    }

    @Test
    @Transactional
    public void test_findByDnaShouldNotBeNull() {
        //Given
        String[] mutantDnaArray = {"TGGAC", "AAGGTT"};
        String[] humanDnaArray = {"TGATC", "CAGGTT"};

        // When
        Optional<Mutant> persistedMutant = mutantRepository.findByDna(mutantDnaArray);
        Optional<Mutant> persistedHuman = mutantRepository.findByDna(humanDnaArray);

        // Then
        assertTrue(persistedMutant.isPresent());
        assertTrue(persistedHuman.isPresent());
        assertEquals(mutant, persistedMutant.get());
        assertEquals(human, persistedHuman.get());
    }

    @Test
    @Transactional
    public void test_getStats() {
        //Given
        String[] mutantDnaArray = {"GGGAT", "CCGGTT"};
        Mutant mutantTwo = new Mutant();
        mutantTwo.setMutant(true);
        mutantTwo.setDna(mutantDnaArray);
        mutantRepository.save(mutantTwo);

        // When
        long mutantCount = mutantRepository.countByIsMutant(true);
        long humanCount = mutantRepository.countByIsMutant(false);

        // Then
        assertEquals(2L, mutantCount);
        assertEquals(1L, humanCount);
    }

}

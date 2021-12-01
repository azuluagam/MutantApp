package com.ml.mutant.service;

import com.ml.mutant.domain.Mutant;
import com.ml.mutant.domain.Stats;
import com.ml.mutant.repository.MutantRepository;
import com.ml.mutant.service.impl.MutantServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


public class MutantServiceImplTest {

    private MutantService mutantService;

    @Mock
    private MutantRepository mutantRepository;

    @Captor
    private ArgumentCaptor<Mutant> mutantArgumentCaptor;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mutantService = new MutantServiceImpl(mutantRepository);
    }

    @Test
    public void test_isMutantShouldReturnTrueAndShouldSaveItWhenNoRecordExists() {
        //Given
        String[] dnaArray = {
                "ATGCGA",
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"};

        given(mutantRepository.findByDna(dnaArray)).willReturn(Optional.empty());

        //When
        boolean result = mutantService.isMutant(dnaArray);

        //Then
        assertTrue(result);
        verify(mutantRepository, times(1)).save(mutantArgumentCaptor.capture());
        assertThat(mutantArgumentCaptor.getValue().getDna()).isEqualTo(dnaArray);
    }

    @Test
    public void test_isMutantShouldReturnFalseAndShouldSaveItWhenNoRecordExists() {
        //Given
        String[] dnaArray = {
                "ATGCGA",
                "CAGTGC",
                "TTATGT",
                "AGATTG",
                "CCCCTA",
                "TCACTG"};

        given(mutantRepository.findByDna(dnaArray)).willReturn(Optional.empty());

        //When
        boolean result = mutantService.isMutant(dnaArray);

        //Then
        assertFalse(result);
        verify(mutantRepository, times(1)).save(mutantArgumentCaptor.capture());
        assertThat(mutantArgumentCaptor.getValue().getDna()).isEqualTo(dnaArray);
    }

    @Test
    public void test_isMutantShouldNotSaveDataWhenRecordExists() {
        //Given
        String[] dnaArray = {
                "ATGCGA",
                "CAGTGC",
                "TTATGT",
                "AGATTG",
                "CCCCTA",
                "TCACTG"};
        Mutant mutant = new Mutant();
        mutant.setId(1L);
        mutant.setDna(dnaArray);
        mutant.setMutant(false);

        given(mutantRepository.findByDna(dnaArray)).willReturn(Optional.of(mutant));

        //When
        boolean result = mutantService.isMutant(dnaArray);

        //Then
        assertFalse(result);
        verify(mutantRepository, times(0)).save(any());
    }

    @Test
    public void test_existsMutantByDnaShouldNotReturnNull() {
        //Given
        String[] dnaArray = {"TGGAC", "AAGGTT"};
        Mutant mutant = new Mutant();
        mutant.setId(1L);
        mutant.setDna(dnaArray);
        mutant.setMutant(false);

        given(mutantRepository.findByDna(dnaArray)).willReturn(Optional.of(mutant));

        //When
        Optional<Mutant> searched = mutantService.existsMutantByDna(dnaArray);

        //Then
        assertTrue(searched.isPresent());
        assertEquals(1L, searched.get().getId());
        assertFalse(searched.get().isMutant());
        assertEquals(dnaArray, searched.get().getDna());
        verify(mutantRepository, times(1)).findByDna(dnaArray);
    }

    @Test
    public void test_existsMutantByDnaShouldReturnNull() {
        //Given
        String[] dnaArray = {"TGGAC", "AAGGTT"};

        given(mutantRepository.findByDna(dnaArray)).willReturn(Optional.empty());

        //When
        Optional<Mutant> searched = mutantService.existsMutantByDna(dnaArray);

        //Then
        assertFalse(searched.isPresent());
        verify(mutantRepository, times(1)).findByDna(dnaArray);
    }

    @Test
    public void test_getMutantStats() {
        //Given
        given(mutantRepository.countByIsMutant(true)).willReturn(40L);
        given(mutantRepository.countByIsMutant(false)).willReturn(100L);

        //When
        Stats stats = mutantService.getMutantStats();

        //Then
        assertNotNull(stats);
        assertEquals(40L, stats.getCountMutantDna());
        assertEquals(100L, stats.getCountHumanDna());
        assertEquals(0.4D, stats.getRatio());
        verify(mutantRepository, times(2)).countByIsMutant(anyBoolean());
    }

}

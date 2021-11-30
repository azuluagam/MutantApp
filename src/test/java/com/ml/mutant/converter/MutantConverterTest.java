package com.ml.mutant.converter;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MutantConverterTest {

    @Test
    public void test_convertToColumnDatabaseShouldReturnString() {
        //Given
        final DnaConverter dnaConverter = new DnaConverter();
        String[] dnaArray = {"TGAACC", "CCTGAC"};

        //When
        String dnaString = dnaConverter.convertToDatabaseColumn(dnaArray);

        //Then
        assertEquals("[\"TGAACC\",\"CCTGAC\"]", dnaString);
    }

    @Test
    public void test_convertToEntityObjectShouldReturnObject() {
        //Given
        final DnaConverter dnaConverter = new DnaConverter();
        String dnaString = "[\"TGAACC\", \"CCTGAC\"]";

        //When
        String[] dnaArray = dnaConverter.convertToEntityAttribute(dnaString);

        //Then
        List<String> expectedDnaList = Arrays.asList("TGAACC", "CCTGAC");
        List<String> dnaList = Arrays.asList(dnaArray);
        assertEquals(expectedDnaList, dnaList);
    }

}

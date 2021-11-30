package com.ml.mutant.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ml.mutant.exception.ConverterException;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;

@Converter
public class DnaConverter implements AttributeConverter<String[], String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(String[] dnaChain) {
        if (dnaChain != null) {
            try {
                return objectMapper.writeValueAsString(dnaChain);
            } catch (JsonProcessingException jsonException) {
                throw new ConverterException(jsonException);
            }
        }

        return null;
    }

    @Override
    public String[] convertToEntityAttribute(String dbData) {
        if (dbData != null) {
            try {
                return objectMapper.readValue(dbData, new TypeReference<String[]>(){});
            } catch (IOException exception) {
                throw new ConverterException(exception);
            }
        }

        return new String[]{};
    }

}

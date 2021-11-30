package com.ml.mutant.domain;

import com.ml.mutant.converter.DnaConverter;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Arrays;
import java.util.Objects;

@Data
@Entity
@Table(name = "mutant")
public class Mutant {

    @Id
    @Column(name = "mutant_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "dna")
    @Convert(converter = DnaConverter.class)
    private String[] dna;

    @Column(name = "is_mutant")
    private boolean isMutant;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mutant mutant = (Mutant) o;
        return isMutant == mutant.isMutant && id.equals(mutant.id) && Arrays.equals(dna, mutant.dna);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, isMutant);
        result = 31 * result + Arrays.hashCode(dna);
        return result;
    }

    @Override
    public String toString() {
        return "Mutant{" +
                "id=" + id +
                ", dna=" + Arrays.toString(dna) +
                ", isMutant=" + isMutant +
                '}';
    }

}

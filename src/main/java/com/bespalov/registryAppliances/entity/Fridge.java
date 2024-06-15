package com.bespalov.registryAppliances.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Fridge extends ModelAppliance {

    @Column(name = "count_of_doors")
    private Integer countOfDoors;

    @Column(name = "type_of_compressor")
    private String typeOfCompressor;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Fridge fridge = (Fridge) o;
        return Objects.equals(countOfDoors, fridge.countOfDoors) && Objects.equals(typeOfCompressor, fridge.typeOfCompressor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), countOfDoors, typeOfCompressor);
    }
}

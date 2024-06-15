package com.bespalov.registryAppliances.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Cleaner extends ModelAppliance {
    @Column(name = "dust_collector_capacity")
    private Integer dustCollectorCapacity;
    @Column(name = "count_of_modes")
    private Integer countOfModes;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Cleaner cleaner = (Cleaner) o;
        return Objects.equals(dustCollectorCapacity, cleaner.dustCollectorCapacity) && Objects.equals(countOfModes, cleaner.countOfModes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), dustCollectorCapacity, countOfModes);
    }
}


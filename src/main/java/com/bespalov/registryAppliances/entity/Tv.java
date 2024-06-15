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
public class Tv extends ModelAppliance {

    @Column(name = "category")
    private String category;

    @Column(name = "technology")
    private String technology;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tv tv = (Tv) o;
        return Objects.equals(category, tv.category) && Objects.equals(technology, tv.technology);
    }

    @Override
    public int hashCode() {
        return Objects.hash(category, technology);
    }
}

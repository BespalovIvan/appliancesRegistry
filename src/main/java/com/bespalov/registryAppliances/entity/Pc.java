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
public class Pc extends ModelAppliance {

    @Column(name = "category")
    private String category;

    @Column(name = "processor")
    private String processor;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Pc pc = (Pc) o;
        return Objects.equals(category, pc.category) && Objects.equals(processor, pc.processor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), category, processor);
    }
}

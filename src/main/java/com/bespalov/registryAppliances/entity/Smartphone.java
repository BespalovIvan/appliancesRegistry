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
public class Smartphone extends ModelAppliance {
    @Column(name = "memory",nullable = false)
    private Integer memory;
    @Column(name = "count_cameras",nullable = false)
    private Integer countCameras;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Smartphone that = (Smartphone) o;
        return Objects.equals(memory, that.memory) && Objects.equals(countCameras, that.countCameras);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), memory, countCameras);
    }
}

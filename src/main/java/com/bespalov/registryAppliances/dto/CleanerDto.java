package com.bespalov.registryAppliances.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CleanerDto extends ModelApplianceDto {
    private Integer dustCollectorCapacity;
    private Integer countOfModes;
}

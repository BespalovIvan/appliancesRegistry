package com.bespalov.registryAppliances.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FridgeDto extends ModelApplianceDto {
    private Integer countOfDoors;
    private String typeOfCompressor;
}

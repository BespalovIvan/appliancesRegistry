package com.bespalov.registryAppliances.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SmartphoneDto extends ModelApplianceDto {
    private Integer memory;
    private Integer countCameras;
}

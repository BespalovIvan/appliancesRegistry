package com.bespalov.registryAppliances.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TvDto extends ModelApplianceDto {
    private String category;
    private String technology;
}

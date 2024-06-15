package com.bespalov.registryAppliances.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PcDto extends ModelApplianceDto {
    private String category;
    private String processor;
}

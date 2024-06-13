package com.bespalov.registryAppliances.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ModelApplianceDto {

    private UUID applianceId;
    private String applianceType;
    private String name;
    private String serialNumber;
    private String color;
    private String size;
    private BigDecimal price;
    private Boolean isAvailability;
}

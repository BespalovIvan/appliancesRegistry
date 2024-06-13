package com.bespalov.registryAppliances.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApplianceDto {
    @NotEmpty(message = "Name cannot be empty")
    private String name;
    @NotEmpty(message = "Manufacturer country cannot be empty")
    private String manufacturerCountry;
    @NotEmpty(message = "Firm cannot be empty")
    private String firm;
    @NotNull(message = "Ordering online cannot be empty")
    private Boolean isOrderOnline;
    @NotNull(message = "Possibility of payment by installments cannot be empty")
    private Boolean isInstallment;
    @NotNull(message = "Appliance type cannot be empty")
    private String applianceType;

}

package com.bespalov.registryAppliances.service;

import com.bespalov.registryAppliances.dto.ModelApplianceDto;

import java.math.BigDecimal;
import java.util.List;

public interface ModelApplianceService {
    List<ModelApplianceDto> findAllModelAppliancesWithSortByAlphabetOrPrice(String direction, String sortParam);

    List<ModelApplianceDto> getModelAppliance(String applianceName, String modelName, String applianceType,
                                              String serialNumber, String color, String size, BigDecimal minPrice,
                                              BigDecimal maxPrice, Boolean availability);
}

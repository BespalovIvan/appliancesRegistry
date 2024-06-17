package com.bespalov.registryAppliances.service;

import com.bespalov.registryAppliances.dto.ModelApplianceDto;

import java.math.BigDecimal;
import java.util.List;

public interface ModelApplianceService {
    List<ModelApplianceDto> findAllModelAppliancesWithSortByAlphabetOrPrice(String direction, String sortParam);

    List<ModelApplianceDto> filterModelAppliance(ModelApplianceDto modelApplianceDto);
}

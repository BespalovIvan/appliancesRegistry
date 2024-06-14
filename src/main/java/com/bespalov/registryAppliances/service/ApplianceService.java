package com.bespalov.registryAppliances.service;

import com.bespalov.registryAppliances.dto.ApplianceDto;

import java.util.List;

public interface ApplianceService {
    ApplianceDto addAppliance(ApplianceDto applianceDto);

    List<ApplianceDto> findAllAppliancesWithSortByAlphabet(String direction);
}

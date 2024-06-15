package com.bespalov.registryAppliances.service;

import com.bespalov.registryAppliances.dto.CleanerDto;
import com.bespalov.registryAppliances.dto.SmartphoneDto;

import java.math.BigDecimal;
import java.util.List;

public interface CleanerService {
    void addCleaner(CleanerDto cleanerDto);

    List<CleanerDto> filterCleaner(String name, String serialNumber, String color, String size,
                                          BigDecimal minPrice, BigDecimal maxPrice, Integer dustCollectorCapacity,
                                          Integer countOfModes, Boolean availability);
}

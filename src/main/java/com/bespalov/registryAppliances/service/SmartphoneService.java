package com.bespalov.registryAppliances.service;

import com.bespalov.registryAppliances.dto.SmartphoneDto;

import java.math.BigDecimal;
import java.util.List;

public interface SmartphoneService {
    void addSmartphone(SmartphoneDto smartphoneDto);

    List<SmartphoneDto> filterSmartphones(String name, String serialNumber, String color, String size,
                                          BigDecimal minPrice, BigDecimal maxPrice, Integer memory,
                                          Integer countCameras, Boolean availability);
}

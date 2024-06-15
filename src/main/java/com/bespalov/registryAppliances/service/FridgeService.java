package com.bespalov.registryAppliances.service;

import com.bespalov.registryAppliances.dto.FridgeDto;

import java.math.BigDecimal;
import java.util.List;

public interface FridgeService {
    void addFridge(FridgeDto fridgeDto);

    List<FridgeDto> filterFridge(String name, String serialNumber, String color, String size,
                                 BigDecimal minPrice, BigDecimal maxPrice, Integer countOfDoors,
                                 String typeOfCompressor, Boolean availability);
}

package com.bespalov.registryAppliances.service;

import com.bespalov.registryAppliances.dto.TvDto;

import java.math.BigDecimal;
import java.util.List;

public interface TvService {
    void addTv(TvDto tvDto);

    List<TvDto> filterTv(String name, String serialNumber, String color, String size,
                         BigDecimal minPrice, BigDecimal maxPrice, String category,
                         String technology, Boolean availability);
}

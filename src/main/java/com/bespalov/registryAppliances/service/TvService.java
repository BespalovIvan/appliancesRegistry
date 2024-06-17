package com.bespalov.registryAppliances.service;

import com.bespalov.registryAppliances.dto.TvDto;

import java.math.BigDecimal;
import java.util.List;

public interface TvService {
    void addTv(TvDto tvDto);

    List<TvDto> filterTv(TvDto tvDto);
}

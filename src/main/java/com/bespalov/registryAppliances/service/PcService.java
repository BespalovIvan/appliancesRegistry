package com.bespalov.registryAppliances.service;

import com.bespalov.registryAppliances.dto.PcDto;

import java.math.BigDecimal;
import java.util.List;

public interface PcService {
    void addPc(PcDto pcDto);

    List<PcDto> filterPc(PcDto pcDto);
}

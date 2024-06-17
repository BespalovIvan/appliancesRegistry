package com.bespalov.registryAppliances.service;

import com.bespalov.registryAppliances.dto.SmartphoneDto;

import java.util.List;

public interface SmartphoneService {
    void addSmartphone(SmartphoneDto smartphoneDto);

    List<SmartphoneDto> filterSmartphones(SmartphoneDto smartphoneDto);
}

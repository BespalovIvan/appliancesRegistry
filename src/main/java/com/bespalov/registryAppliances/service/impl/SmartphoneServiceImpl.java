package com.bespalov.registryAppliances.service.impl;

import com.bespalov.registryAppliances.dto.ApplianceType;
import com.bespalov.registryAppliances.dto.SmartphoneDto;
import com.bespalov.registryAppliances.entity.Appliance;
import com.bespalov.registryAppliances.entity.Smartphone;
import com.bespalov.registryAppliances.repository.ApplianceRepository;
import com.bespalov.registryAppliances.repository.SmartphoneRepository;
import com.bespalov.registryAppliances.service.SmartphoneService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class SmartphoneServiceImpl implements SmartphoneService {
    private final SmartphoneRepository smartphoneRepository;
    private final ApplianceRepository applianceRepository;
    private final ModelMapper modelMapper;

    public SmartphoneServiceImpl(SmartphoneRepository smartphoneRepository,
                                 ApplianceRepository applianceRepository, ModelMapper modelMapper) {
        this.smartphoneRepository = smartphoneRepository;
        this.applianceRepository = applianceRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    @Override
    public void addSmartphone(SmartphoneDto smartphoneDto) {
        Appliance appliance = applianceRepository
                .findById(smartphoneDto.getApplianceId())
                .orElseThrow(RuntimeException::new);
        Smartphone smartphone = convertToSmartphone(smartphoneDto);
        smartphone.setAppliance(appliance);
        if (smartphone.getIsAvailability()) {
            appliance.setModelAvailable(appliance.getModelAvailable() + 1);
            applianceRepository.save(appliance);
        }
        smartphoneRepository.save(smartphone);
    }

    public Smartphone convertToSmartphone(SmartphoneDto smartphoneDto) {
        return modelMapper.map(smartphoneDto, Smartphone.class);
    }
}

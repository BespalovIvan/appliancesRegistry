package com.bespalov.registryAppliances.service.impl;

import com.bespalov.registryAppliances.dto.SmartphoneDto;
import com.bespalov.registryAppliances.entity.Appliance;
import com.bespalov.registryAppliances.entity.Smartphone;
import com.bespalov.registryAppliances.repository.ApplianceRepository;
import com.bespalov.registryAppliances.repository.SmartphoneRepository;
import com.bespalov.registryAppliances.service.SmartphoneService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public List<SmartphoneDto> filterSmartphones(String name, String serialNumber, String color, String size,
                                                 BigDecimal minPrice, BigDecimal maxPrice, Integer memory,
                                                 Integer countCameras, Boolean availability) {
        List<Smartphone> allSmartphones = smartphoneRepository.findAll();
        List<SmartphoneDto> smartphoneDtoList = new ArrayList<>();
        allSmartphones.forEach((smartphone) -> smartphoneDtoList.add(convertToSmartphoneDto(smartphone)));
        return smartphoneDtoList
                .stream()
                .filter(smartphoneDto -> name == null || smartphoneDto
                        .getName()
                        .equalsIgnoreCase(name))
                .filter(smartphoneDto -> serialNumber == null || smartphoneDto
                        .getSerialNumber()
                        .equalsIgnoreCase(serialNumber))
                .filter(smartphoneDto -> color == null || smartphoneDto
                        .getColor()
                        .equalsIgnoreCase(color))
                .filter(smartphoneDto -> size == null || smartphoneDto
                        .getSize()
                        .equalsIgnoreCase(size))
                .filter(smartphoneDto -> minPrice == null || (smartphoneDto
                        .getPrice())
                        .compareTo(minPrice) >= 0)
                .filter(smartphoneDto -> maxPrice == null || (smartphoneDto
                        .getPrice())
                        .compareTo(maxPrice) <= 0)
                .filter(smartphoneDto -> memory == null || smartphoneDto
                        .getMemory()
                        .equals(memory))
                .filter(smartphoneDto -> countCameras == null || smartphoneDto
                        .getCountCameras()
                        .equals(countCameras))
                .filter(smartphoneDto -> availability == null || smartphoneDto
                        .getIsAvailability()
                        .equals(availability))
                .collect(Collectors.toList());
    }

    public Smartphone convertToSmartphone(SmartphoneDto smartphoneDto) {
        return modelMapper.map(smartphoneDto, Smartphone.class);
    }

    public SmartphoneDto convertToSmartphoneDto(Smartphone smartphone) {
        return modelMapper.map(smartphone, SmartphoneDto.class);
    }
}

package com.bespalov.registryAppliances.service.impl;

import com.bespalov.registryAppliances.config.QPredicate;
import com.bespalov.registryAppliances.dto.SmartphoneDto;
import com.bespalov.registryAppliances.entity.Appliance;
import com.bespalov.registryAppliances.entity.Smartphone;
import com.bespalov.registryAppliances.repository.ApplianceRepository;
import com.bespalov.registryAppliances.repository.SmartphoneRepository;
import com.bespalov.registryAppliances.service.SmartphoneService;
import com.querydsl.core.types.Predicate;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.bespalov.registryAppliances.entity.QSmartphone.smartphone;

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

    @Transactional
    @Override
    public List<SmartphoneDto> filterSmartphones(SmartphoneDto smartphoneDto) {
        Predicate predicate = QPredicate.builder()
                .add(smartphoneDto.getName(), smartphone.name::containsIgnoreCase)
                .add(smartphoneDto.getSerialNumber(), smartphone.serialNumber::containsIgnoreCase)
                .add(smartphoneDto.getColor(), smartphone.color::containsIgnoreCase)
                .add(smartphoneDto.getSize(), smartphone.size::containsIgnoreCase)
                .add(smartphoneDto.getPrice(), smartphone.price::goe)
                .add(smartphoneDto.getIsAvailability(), smartphone.isAvailability::eq)
                .add(smartphoneDto.getMemory(), smartphone.memory::eq)
                .add(smartphoneDto.getCountCameras(), smartphone.countCameras::eq)
                .buildOr();
        Iterable<Smartphone> smartphones = smartphoneRepository.findAll(predicate);
        List<SmartphoneDto> result = new ArrayList<>();
        smartphones.iterator().forEachRemaining((smartphone) -> result.add(convertToSmartphoneDto(smartphone)));
        return result;
    }

    public Smartphone convertToSmartphone(SmartphoneDto smartphoneDto) {
        return modelMapper.map(smartphoneDto, Smartphone.class);
    }

    public SmartphoneDto convertToSmartphoneDto(Smartphone smartphone) {
        return modelMapper.map(smartphone, SmartphoneDto.class);
    }
}

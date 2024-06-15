package com.bespalov.registryAppliances.service.impl;

import com.bespalov.registryAppliances.dto.CleanerDto;
import com.bespalov.registryAppliances.entity.Appliance;
import com.bespalov.registryAppliances.entity.Cleaner;
import com.bespalov.registryAppliances.repository.ApplianceRepository;
import com.bespalov.registryAppliances.repository.CleanerRepository;
import com.bespalov.registryAppliances.service.CleanerService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CleanerServiceImpl implements CleanerService {
    private final CleanerRepository cleanerRepository;
    private final ApplianceRepository applianceRepository;
    private final ModelMapper modelMapper;

    public CleanerServiceImpl(CleanerRepository cleanerRepository, ApplianceRepository applianceRepository,
                              ModelMapper modelMapper) {
        this.cleanerRepository = cleanerRepository;
        this.applianceRepository = applianceRepository;
        this.modelMapper = modelMapper;
    }


    @Transactional
    @Override
    public void addCleaner(CleanerDto cleanerDto) {
        Appliance appliance = applianceRepository
                .findById(cleanerDto.getApplianceId())
                .orElseThrow(RuntimeException::new);
        Cleaner cleaner = convertToCleaner(cleanerDto);
        cleaner.setAppliance(appliance);
        if (cleaner.getIsAvailability()) {
            appliance.setModelAvailable(appliance.getModelAvailable() + 1);
            applianceRepository.save(appliance);
        }
        cleanerRepository.save(cleaner);
    }

    @Transactional
    @Override
    public List<CleanerDto> filterCleaner(String name, String serialNumber, String color, String size,
                                          BigDecimal minPrice, BigDecimal maxPrice, Integer dustCollectorCapacity,
                                          Integer countOfModes, Boolean availability) {
        List<Cleaner> allCleaners = cleanerRepository.findAll();
        List<CleanerDto> cleanerDtoList = new ArrayList<>();
        allCleaners.forEach((cleaner) -> cleanerDtoList.add(convertToCleanerDto(cleaner)));
        return cleanerDtoList
                .stream()
                .filter(cleanerDto -> name == null || cleanerDto
                        .getName()
                        .equalsIgnoreCase(name))
                .filter(cleanerDto -> serialNumber == null || cleanerDto
                        .getSerialNumber()
                        .equalsIgnoreCase(serialNumber))
                .filter(cleanerDto -> color == null || cleanerDto
                        .getColor()
                        .equalsIgnoreCase(color))
                .filter(cleanerDto -> size == null || cleanerDto
                        .getSize()
                        .equalsIgnoreCase(size))
                .filter(cleanerDto -> minPrice == null || cleanerDto
                        .getPrice()
                        .compareTo(minPrice) >= 0)
                .filter(cleanerDto -> maxPrice == null || cleanerDto
                        .getPrice()
                        .compareTo(maxPrice) <= 0)
                .filter(cleanerDto -> dustCollectorCapacity == null || cleanerDto
                        .getDustCollectorCapacity()
                        .equals(dustCollectorCapacity))
                .filter(cleanerDto -> countOfModes == null || cleanerDto
                        .getCountOfModes()
                        .equals(countOfModes))
                .filter(cleanerDto -> availability == null || cleanerDto
                        .getIsAvailability()
                        .equals(availability))
                .collect(Collectors.toList());
    }

    public Cleaner convertToCleaner(CleanerDto cleanerDto) {
        return modelMapper.map(cleanerDto, Cleaner.class);
    }

    public CleanerDto convertToCleanerDto(Cleaner cleaner) {
        return modelMapper.map(cleaner, CleanerDto.class);
    }
}

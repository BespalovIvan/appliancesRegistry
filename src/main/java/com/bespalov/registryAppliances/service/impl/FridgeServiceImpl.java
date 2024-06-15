package com.bespalov.registryAppliances.service.impl;

import com.bespalov.registryAppliances.dto.FridgeDto;
import com.bespalov.registryAppliances.entity.Appliance;
import com.bespalov.registryAppliances.entity.Fridge;
import com.bespalov.registryAppliances.repository.ApplianceRepository;
import com.bespalov.registryAppliances.repository.FridgeRepository;
import com.bespalov.registryAppliances.service.FridgeService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FridgeServiceImpl implements FridgeService {
    private final FridgeRepository fridgeRepository;
    private final ApplianceRepository applianceRepository;
    private final ModelMapper modelMapper;

    public FridgeServiceImpl(FridgeRepository fridgeRepository, ApplianceRepository applianceRepository,
                             ModelMapper modelMapper) {
        this.fridgeRepository = fridgeRepository;
        this.applianceRepository = applianceRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    @Override
    public void addFridge(FridgeDto fridgeDto) {
        Appliance appliance = applianceRepository
                .findById(fridgeDto.getApplianceId())
                .orElseThrow(RuntimeException::new);
        Fridge fridge = convertToFridge(fridgeDto);
        fridge.setAppliance(appliance);
        if (fridge.getIsAvailability()) {
            appliance.setModelAvailable(appliance.getModelAvailable() + 1);
            applianceRepository.save(appliance);
        }
        fridgeRepository.save(fridge);
    }

    @Transactional
    @Override
    public List<FridgeDto> filterFridge(String name, String serialNumber, String color, String size,
                                        BigDecimal minPrice, BigDecimal maxPrice, Integer countOfDoors,
                                        String typeOfCompressor, Boolean availability) {
        List<Fridge> allFridges = fridgeRepository.findAll();
        List<FridgeDto> fridgeDtoList = new ArrayList<>();
        allFridges.forEach((fridge) -> fridgeDtoList.add(convertToFridgeDto(fridge)));
        return fridgeDtoList
                .stream()
                .filter(fridgeDto -> name == null || fridgeDto
                        .getName()
                        .equalsIgnoreCase(name))
                .filter(fridgeDto -> serialNumber == null || fridgeDto
                        .getSerialNumber()
                        .equalsIgnoreCase(serialNumber))
                .filter(fridgeDto -> color == null || fridgeDto
                        .getColor()
                        .equalsIgnoreCase(color))
                .filter(fridgeDto -> size == null || fridgeDto
                        .getSize()
                        .equalsIgnoreCase(size))
                .filter(fridgeDto -> minPrice == null || fridgeDto
                        .getPrice()
                        .compareTo(minPrice) >= 0)
                .filter(fridgeDto -> maxPrice == null || fridgeDto
                        .getPrice()
                        .compareTo(maxPrice) <= 0)
                .filter(fridgeDto -> countOfDoors == null || fridgeDto
                        .getCountOfDoors()
                        .equals(countOfDoors))
                .filter(fridgeDto -> typeOfCompressor == null || fridgeDto
                        .getTypeOfCompressor()
                        .equalsIgnoreCase(typeOfCompressor))
                .filter(cleanerDto -> availability == null || cleanerDto
                        .getIsAvailability()
                        .equals(availability))
                .collect(Collectors.toList());
    }

    public Fridge convertToFridge(FridgeDto fridgeDto) {
        return modelMapper.map(fridgeDto, Fridge.class);
    }

    public FridgeDto convertToFridgeDto(Fridge fridge) {
        return modelMapper.map(fridge, FridgeDto.class);
    }
}

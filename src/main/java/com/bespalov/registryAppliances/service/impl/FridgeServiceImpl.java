package com.bespalov.registryAppliances.service.impl;

import com.bespalov.registryAppliances.config.QPredicate;
import com.bespalov.registryAppliances.dto.FridgeDto;
import com.bespalov.registryAppliances.entity.Appliance;
import com.bespalov.registryAppliances.entity.Fridge;
import com.bespalov.registryAppliances.repository.ApplianceRepository;
import com.bespalov.registryAppliances.repository.FridgeRepository;
import com.bespalov.registryAppliances.service.FridgeService;
import com.querydsl.core.types.Predicate;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static com.bespalov.registryAppliances.entity.QFridge.fridge;

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
    public List<FridgeDto> filterFridge(FridgeDto fridgeDto) {
        Predicate predicate = QPredicate.builder()
                .add(fridgeDto.getName(), fridge.name::containsIgnoreCase)
                .add(fridgeDto.getSerialNumber(), fridge.serialNumber::containsIgnoreCase)
                .add(fridgeDto.getColor(), fridge.color::containsIgnoreCase)
                .add(fridgeDto.getSize(), fridge.size::containsIgnoreCase)
                .add(fridgeDto.getPrice(), fridge.price::goe)
                .add(fridgeDto.getIsAvailability(), fridge.isAvailability::eq)
                .add(fridgeDto.getCountOfDoors(), fridge.countOfDoors::eq)
                .add(fridgeDto.getTypeOfCompressor(), fridge.typeOfCompressor::containsIgnoreCase)
                .buildOr();
        Iterable<Fridge> fridges = fridgeRepository.findAll(predicate);
        List<FridgeDto> result = new ArrayList<>();
        fridges.iterator().forEachRemaining((fridge) -> result.add(convertToFridgeDto(fridge)));
        return result;
    }

    public Fridge convertToFridge(FridgeDto fridgeDto) {
        return modelMapper.map(fridgeDto, Fridge.class);
    }

    public FridgeDto convertToFridgeDto(Fridge fridge) {
        return modelMapper.map(fridge, FridgeDto.class);
    }
}

package com.bespalov.registryAppliances.service.impl;

import com.bespalov.registryAppliances.dto.ModelApplianceDto;
import com.bespalov.registryAppliances.entity.ModelAppliance;
import com.bespalov.registryAppliances.repository.ModelApplianceRepository;
import com.bespalov.registryAppliances.service.ModelApplianceService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ModelApplianceServiceImpl implements ModelApplianceService {
    private final ModelApplianceRepository modelApplianceRepository;
    private final ModelMapper modelMapper;

    public ModelApplianceServiceImpl(ModelApplianceRepository modelApplianceRepository, ModelMapper modelMapper) {
        this.modelApplianceRepository = modelApplianceRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    @Override
    public List<ModelApplianceDto> findAllModelAppliancesWithSortByAlphabetOrPrice(String direction,
                                                                                   String sortParam) {
        Sort sort = Sort.by(sortParam);
        if ("asc".equalsIgnoreCase(direction)) {
            sort = sort.ascending();
        } else if ("desc".equalsIgnoreCase(direction)) {
            sort = sort.descending();
        } else {
            sort = sort.ascending();
        }
        List<ModelAppliance> modelApplianceList = modelApplianceRepository.findAll(sort);
        List<ModelApplianceDto> modelApplianceDtoList = new ArrayList<>();
        modelApplianceList.forEach((modelAppliance) -> modelApplianceDtoList.add(convertToDto(modelAppliance)));
        return modelApplianceDtoList;
    }

    @Transactional
    @Override
    public List<ModelApplianceDto> getModelAppliance(String applianceName, String modelName, String applianceType,
                                                     String serialNumber, String color,
                                                     String size, BigDecimal minPrice, BigDecimal maxPrice,
                                                     Boolean availability) {
        List<ModelAppliance> modelApplianceList = modelApplianceRepository.findAll();
        return modelApplianceList.stream()
                .filter(model -> applianceName == null || model.getAppliance()
                        .getName()
                        .equalsIgnoreCase(applianceName))
                .filter(model-> modelName == null || model
                        .getName()
                        .equalsIgnoreCase(modelName))
                .filter(model -> applianceType == null || model
                        .getApplianceType().toString()
                        .equalsIgnoreCase(applianceType))
                .filter(model -> serialNumber == null || model
                        .getSerialNumber()
                        .equalsIgnoreCase(serialNumber))
                .filter(model -> color == null || model
                        .getColor()
                        .equalsIgnoreCase(color))
                .filter(model -> size == null || model
                        .getSize()
                        .equalsIgnoreCase(size))
                .filter(model -> minPrice == null || (model
                        .getPrice())
                        .compareTo(minPrice) >= 0)
                .filter(model -> maxPrice == null || (model
                        .getPrice())
                        .compareTo(maxPrice) <= 0)
                .filter(model -> availability == null || model
                        .getIsAvailability()
                        .equals(availability))
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public ModelApplianceDto convertToDto(ModelAppliance modelAppliance) {
        return modelMapper.map(modelAppliance, ModelApplianceDto.class);
    }
}

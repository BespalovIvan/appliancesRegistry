package com.bespalov.registryAppliances.service.impl;

import com.bespalov.registryAppliances.config.QPredicate;
import com.bespalov.registryAppliances.dto.ApplianceType;
import com.bespalov.registryAppliances.dto.ModelApplianceDto;
import com.bespalov.registryAppliances.entity.ModelAppliance;
import com.bespalov.registryAppliances.repository.ModelApplianceRepository;
import com.bespalov.registryAppliances.service.ModelApplianceService;
import com.querydsl.core.types.Predicate;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.bespalov.registryAppliances.entity.QModelAppliance.modelAppliance;

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
    public List<ModelApplianceDto> filterModelAppliance(ModelApplianceDto modelApplianceDto) {
        Predicate predicate = QPredicate.builder()
                .add(ApplianceType.valueOf(modelApplianceDto.getApplianceType()),
                        modelAppliance.applianceType::eq)
                .add(modelApplianceDto.getName(), modelAppliance.name::containsIgnoreCase)
                .add(modelApplianceDto.getSerialNumber(), modelAppliance.serialNumber::containsIgnoreCase)
                .add(modelApplianceDto.getColor(), modelAppliance.color::containsIgnoreCase)
                .add(modelApplianceDto.getSize(), modelAppliance.size::containsIgnoreCase)
                .add(modelApplianceDto.getPrice(), modelAppliance.price::goe)
                .add(modelApplianceDto.getIsAvailability(), modelAppliance.isAvailability::eq)
                .buildOr();
        Iterable<ModelAppliance> modelApplianceIterable = modelApplianceRepository.findAll(predicate);
        List<ModelApplianceDto> result = new ArrayList<>();
        modelApplianceIterable.iterator().forEachRemaining((modelAppliance) -> result.add(convertToDto(modelAppliance)));
        return result;
    }

    public ModelApplianceDto convertToDto(ModelAppliance modelAppliance) {
        return modelMapper.map(modelAppliance, ModelApplianceDto.class);
    }
}

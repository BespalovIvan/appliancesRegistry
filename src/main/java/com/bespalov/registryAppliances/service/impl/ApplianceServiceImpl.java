package com.bespalov.registryAppliances.service.impl;

import com.bespalov.registryAppliances.dto.ApplianceDto;
import com.bespalov.registryAppliances.entity.Appliance;
import com.bespalov.registryAppliances.repository.ApplianceRepository;
import com.bespalov.registryAppliances.service.ApplianceService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class ApplianceServiceImpl implements ApplianceService {
    private final ApplianceRepository applianceRepository;
    private final ModelMapper modelMapper;

    public ApplianceServiceImpl(ApplianceRepository applianceRepository, ModelMapper modelMapper) {
        this.applianceRepository = applianceRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    @Override
    public ApplianceDto addAppliance(ApplianceDto applianceDto) {
        Appliance appliance = convertToAppliance(applianceDto);
        appliance.setModelAvailable(0);
        return convertToDto(applianceRepository.save(appliance));
    }

    @Transactional
    @Override
    public List<ApplianceDto> findAllAppliancesWithSortByAlphabet(String direction) {
        Sort sort = Sort.by("name");
        if ("asc".equalsIgnoreCase(direction)) {
            sort = sort.ascending();
        } else if ("desc".equalsIgnoreCase(direction)) {
            sort = sort.descending();
        } else {
            sort = sort.ascending();
        }
        List<Appliance> applianceList = applianceRepository.findAll(sort);
        List<ApplianceDto> applianceDtoList = new ArrayList<>();
        applianceList.forEach((appliance) -> applianceDtoList.add(convertToDto(appliance)));
        return applianceDtoList;
    }

    public ApplianceDto convertToDto(Appliance appliance) {
        return modelMapper.map(appliance, ApplianceDto.class);
    }

    public Appliance convertToAppliance(ApplianceDto applianceDto) {
        return modelMapper.map(applianceDto, Appliance.class);
    }

}

package com.bespalov.registryAppliances.service.impl;

import com.bespalov.registryAppliances.dto.ApplianceDto;
import com.bespalov.registryAppliances.entity.Appliance;
import com.bespalov.registryAppliances.repository.ApplianceRepository;
import com.bespalov.registryAppliances.service.ApplianceService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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

    public ApplianceDto convertToDto(Appliance appliance) {
        return modelMapper.map(appliance, ApplianceDto.class);
    }

    public Appliance convertToAppliance(ApplianceDto applianceDto) {
        return modelMapper.map(applianceDto, Appliance.class);
    }

}

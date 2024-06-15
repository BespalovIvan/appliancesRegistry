package com.bespalov.registryAppliances.service.impl;

import com.bespalov.registryAppliances.dto.PcDto;
import com.bespalov.registryAppliances.entity.Appliance;
import com.bespalov.registryAppliances.entity.Pc;
import com.bespalov.registryAppliances.repository.ApplianceRepository;
import com.bespalov.registryAppliances.repository.PcRepository;
import com.bespalov.registryAppliances.service.PcService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PcServiceImpl implements PcService {
    private final PcRepository pcRepository;
    private final ApplianceRepository applianceRepository;
    private final ModelMapper modelMapper;

    public PcServiceImpl(PcRepository pcRepository, ApplianceRepository applianceRepository,
                         ModelMapper modelMapper) {
        this.pcRepository = pcRepository;
        this.applianceRepository = applianceRepository;
        this.modelMapper = modelMapper;
    }


    @Transactional
    @Override
    public void addPc(PcDto pcDto) {
        Appliance appliance = applianceRepository
                .findById(pcDto.getApplianceId())
                .orElseThrow(RuntimeException::new);
        Pc pc = convertToPc(pcDto);
        pc.setAppliance(appliance);
        if (pc.getIsAvailability()) {
            appliance.setModelAvailable(appliance.getModelAvailable() + 1);
            applianceRepository.save(appliance);
        }
        pcRepository.save(pc);
    }

    @Transactional
    @Override
    public List<PcDto> filterPc(String name, String serialNumber, String color, String size,
                                BigDecimal minPrice, BigDecimal maxPrice, String category, String processor,
                                Boolean availability) {
        List<Pc> allPc = pcRepository.findAll();
        List<PcDto> pcDtoList = new ArrayList<>();
        allPc.forEach((pc) -> pcDtoList.add(convertToPcDto(pc)));
        return pcDtoList
                .stream()
                .filter(pcDto -> name == null || pcDto
                        .getName()
                        .equalsIgnoreCase(name))
                .filter(pcDto -> serialNumber == null || pcDto
                        .getSerialNumber()
                        .equalsIgnoreCase(serialNumber))
                .filter(pcDto -> color == null || pcDto
                        .getColor()
                        .equalsIgnoreCase(color))
                .filter(pcDto -> size == null || pcDto
                        .getSize()
                        .equalsIgnoreCase(size))
                .filter(pcDto -> minPrice == null || pcDto
                        .getPrice()
                        .compareTo(minPrice) >= 0)
                .filter(pcDto -> maxPrice == null || pcDto
                        .getPrice()
                        .compareTo(maxPrice) <= 0)
                .filter(pcDto -> category == null || pcDto
                        .getCategory()
                        .equalsIgnoreCase(category))
                .filter(pcDto -> processor == null || pcDto
                        .getProcessor()
                        .equalsIgnoreCase(processor))
                .filter(cleanerDto -> availability == null || cleanerDto
                        .getIsAvailability()
                        .equals(availability))
                .collect(Collectors.toList());
    }

    public Pc convertToPc(PcDto pcDto) {
        return modelMapper.map(pcDto, Pc.class);
    }

    public PcDto convertToPcDto(Pc pc) {
        return modelMapper.map(pc, PcDto.class);
    }
}

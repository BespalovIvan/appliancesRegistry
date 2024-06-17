package com.bespalov.registryAppliances.service.impl;

import com.bespalov.registryAppliances.config.QPredicate;
import com.bespalov.registryAppliances.dto.PcDto;
import com.bespalov.registryAppliances.entity.Appliance;
import com.bespalov.registryAppliances.entity.Pc;
import com.bespalov.registryAppliances.entity.QPc;
import com.bespalov.registryAppliances.repository.ApplianceRepository;
import com.bespalov.registryAppliances.repository.PcRepository;
import com.bespalov.registryAppliances.service.PcService;
import com.querydsl.core.types.Predicate;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.bespalov.registryAppliances.entity.QPc.*;

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
    public List<PcDto> filterPc(PcDto pcDto) {
        Predicate predicate = QPredicate.builder()
                .add(pcDto.getName(), pc.name::containsIgnoreCase)
                .add(pcDto.getSerialNumber(), pc.serialNumber::containsIgnoreCase)
                .add(pcDto.getColor(), pc.color::containsIgnoreCase)
                .add(pcDto.getSize(), pc.size::containsIgnoreCase)
                .add(pcDto.getPrice(), pc.price::goe)
                .add(pcDto.getIsAvailability(), pc.isAvailability::eq)
                .add(pcDto.getProcessor(), pc.processor::containsIgnoreCase)
                .add(pcDto.getCategory(), pc.category::containsIgnoreCase)
                .buildOr();
        Iterable<Pc> pcIterable = pcRepository.findAll(predicate);
        List<PcDto> result = new ArrayList<>();
        pcIterable.iterator().forEachRemaining((pc) -> result.add(convertToPcDto(pc)));
        return result;
    }

    public Pc convertToPc(PcDto pcDto) {
        return modelMapper.map(pcDto, Pc.class);
    }

    public PcDto convertToPcDto(Pc pc) {
        return modelMapper.map(pc, PcDto.class);
    }
}

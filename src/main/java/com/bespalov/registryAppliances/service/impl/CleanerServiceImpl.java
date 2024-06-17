package com.bespalov.registryAppliances.service.impl;

import com.bespalov.registryAppliances.config.QPredicate;
import com.bespalov.registryAppliances.dto.CleanerDto;
import com.bespalov.registryAppliances.entity.Appliance;
import com.bespalov.registryAppliances.entity.Cleaner;
import com.bespalov.registryAppliances.repository.ApplianceRepository;
import com.bespalov.registryAppliances.repository.CleanerRepository;
import com.bespalov.registryAppliances.service.CleanerService;
import com.querydsl.core.types.Predicate;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.bespalov.registryAppliances.entity.QCleaner.cleaner;

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
    public List<CleanerDto> filterCleaner(CleanerDto cleanerDto) {
        Predicate predicate = QPredicate.builder()
                .add(cleanerDto.getName(), cleaner.name::containsIgnoreCase)
                .add(cleanerDto.getSerialNumber(), cleaner.serialNumber::containsIgnoreCase)
                .add(cleanerDto.getColor(), cleaner.color::containsIgnoreCase)
                .add(cleanerDto.getSize(), cleaner.size::containsIgnoreCase)
                .add(cleanerDto.getSize(), cleaner.size::containsIgnoreCase)
                .add(cleanerDto.getIsAvailability(), cleaner.isAvailability::eq)
                .add(cleanerDto.getDustCollectorCapacity(), cleaner.dustCollectorCapacity::eq)
                .add(cleanerDto.getCountOfModes(), cleaner.countOfModes::eq)
                .buildOr();
        Iterable<Cleaner> cleaners = cleanerRepository.findAll(predicate);
        List<CleanerDto> result = new ArrayList<>();
        cleaners.iterator().forEachRemaining((cleaner) -> result.add(convertToCleanerDto(cleaner)));
        return result;
    }

    public Cleaner convertToCleaner(CleanerDto cleanerDto) {
        return modelMapper.map(cleanerDto, Cleaner.class);
    }

    public CleanerDto convertToCleanerDto(Cleaner cleaner) {
        return modelMapper.map(cleaner, CleanerDto.class);
    }
}

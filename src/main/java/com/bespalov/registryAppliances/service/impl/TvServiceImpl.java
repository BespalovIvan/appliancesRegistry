package com.bespalov.registryAppliances.service.impl;

import com.bespalov.registryAppliances.dto.TvDto;
import com.bespalov.registryAppliances.entity.Appliance;
import com.bespalov.registryAppliances.entity.Tv;
import com.bespalov.registryAppliances.repository.ApplianceRepository;
import com.bespalov.registryAppliances.repository.TvRepository;
import com.bespalov.registryAppliances.service.TvService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TvServiceImpl implements TvService {
    private final TvRepository tvRepository;
    private final ApplianceRepository applianceRepository;
    private final ModelMapper modelMapper;

    public TvServiceImpl(TvRepository tvRepository, ApplianceRepository applianceRepository,
                         ModelMapper modelMapper) {
        this.tvRepository = tvRepository;
        this.applianceRepository = applianceRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    @Override
    public void addTv(TvDto tvDto) {
        Appliance appliance = applianceRepository
                .findById(tvDto.getApplianceId())
                .orElseThrow(RuntimeException::new);
        Tv tv = convertToTv(tvDto);
        tv.setAppliance(appliance);
        if (tv.getIsAvailability()) {
            appliance.setModelAvailable(appliance.getModelAvailable() + 1);
            applianceRepository.save(appliance);
        }
        tvRepository.save(tv);
    }

    @Transactional
    @Override
    public List<TvDto> filterTv(String name, String serialNumber, String color, String size, BigDecimal minPrice,
                                BigDecimal maxPrice, String category, String technology,
                                Boolean availability) {
        List<Tv> allTv = tvRepository.findAll();
        List<TvDto> tvDtoList = new ArrayList<>();
        allTv.forEach((tv) -> tvDtoList.add(convertToTvDto(tv)));
        return tvDtoList
                .stream()
                .filter(tvDto -> name == null || tvDto
                        .getName()
                        .equalsIgnoreCase(name))
                .filter(tvDto -> serialNumber == null || tvDto
                        .getSerialNumber()
                        .equalsIgnoreCase(serialNumber))
                .filter(tvDto -> color == null || tvDto
                        .getColor()
                        .equalsIgnoreCase(color))
                .filter(tvDto -> size == null || tvDto
                        .getSize()
                        .equalsIgnoreCase(size))
                .filter(tvDto -> minPrice == null || tvDto
                        .getPrice()
                        .compareTo(minPrice) >= 0)
                .filter(tvDto -> maxPrice == null || tvDto
                        .getPrice()
                        .compareTo(maxPrice) <= 0)
                .filter(tvDto -> category == null || tvDto
                        .getCategory()
                        .equalsIgnoreCase(category))
                .filter(tvDto -> technology == null || tvDto
                        .getTechnology()
                        .equalsIgnoreCase(technology))
                .filter(cleanerDto -> availability == null || cleanerDto
                        .getIsAvailability()
                        .equals(availability))
                .collect(Collectors.toList());
    }

    public Tv convertToTv(TvDto tvDto) {
        return modelMapper.map(tvDto, Tv.class);
    }

    public TvDto convertToTvDto(Tv tv) {
        return modelMapper.map(tv, TvDto.class);
    }
}

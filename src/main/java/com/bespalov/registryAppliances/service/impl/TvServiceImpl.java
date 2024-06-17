package com.bespalov.registryAppliances.service.impl;

import com.bespalov.registryAppliances.config.QPredicate;
import com.bespalov.registryAppliances.dto.TvDto;
import com.bespalov.registryAppliances.entity.Appliance;
import com.bespalov.registryAppliances.entity.Tv;
import com.bespalov.registryAppliances.repository.ApplianceRepository;
import com.bespalov.registryAppliances.repository.TvRepository;
import com.bespalov.registryAppliances.service.TvService;
import com.querydsl.core.types.Predicate;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static com.bespalov.registryAppliances.entity.QTv.tv;

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
    public List<TvDto> filterTv(TvDto tvDto) {
        Predicate predicate = QPredicate.builder()
                .add(tvDto.getName(), tv.name::containsIgnoreCase)
                .add(tvDto.getSerialNumber(), tv.serialNumber::containsIgnoreCase)
                .add(tvDto.getColor(), tv.color::containsIgnoreCase)
                .add(tvDto.getSize(), tv.size::containsIgnoreCase)
                .add(tvDto.getPrice(), tv.price::goe)
                .add(tvDto.getIsAvailability(), tv.isAvailability::eq)
                .add(tvDto.getCategory(), tv.category::containsIgnoreCase)
                .add(tvDto.getTechnology(), tv.technology::containsIgnoreCase)
                .buildOr();
        Iterable<Tv> tvIterable = tvRepository.findAll(predicate);
        List<TvDto> result = new ArrayList<>();
        tvIterable.iterator().forEachRemaining((tv) -> result.add(convertToTvDto(tv)));
        return result;
    }

    public Tv convertToTv(TvDto tvDto) {
        return modelMapper.map(tvDto, Tv.class);
    }

    public TvDto convertToTvDto(Tv tv) {
        return modelMapper.map(tv, TvDto.class);
    }
}

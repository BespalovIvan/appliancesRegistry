package com.bespalov.registryAppliances.controller;

import com.bespalov.registryAppliances.dto.ModelApplianceDto;
import com.bespalov.registryAppliances.service.ModelApplianceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/models")
public class ModelApplianceController {
    private final ModelApplianceService modelApplianceService;

    public ModelApplianceController(ModelApplianceService modelApplianceService) {
        this.modelApplianceService = modelApplianceService;
    }

    @Operation(summary = "Get all model appliances with sorting by alphabet or price", tags = {"modelAppliance"})
    @GetMapping("/sort")
    public ResponseEntity<List<ModelApplianceDto>> sortingAllModels(
            @Parameter(description = "Sort direction meaning", required = true)
            @RequestParam(defaultValue = "asc")
            String direction,
            @Parameter(description = "Sorting parameter", required = true)
            @RequestParam(defaultValue = "name")
            String sortParam) {
        return new ResponseEntity<>(modelApplianceService
                .findAllModelAppliancesWithSortByAlphabetOrPrice(direction, sortParam), HttpStatus.OK);
    }

    @Operation(summary = "Getting and filtering appliance models ", tags = {"modelAppliance"})
    @GetMapping("/filter")
    public List<ModelApplianceDto> getModelApplianceAndFilter(
            @Parameter(description = "Name of the appliance ")
            @RequestParam(required = false) String applianceName,
            @Parameter(description = "Name of the model")
            @RequestParam(required = false) String modelName,
            @Parameter(description = "Appliance type")
            @RequestParam(required = false) String applianceType,
            @Parameter(description = "Serial number appliance")
            @RequestParam(required = false) String serialNumber,
            @Parameter(description = "Color appliance")
            @RequestParam(required = false) String color,
            @Parameter(description = "Size appliance")
            @RequestParam(required = false) String size,
            @Parameter(description = "Min price appliance")
            @RequestParam(required = false) BigDecimal minPrice,
            @Parameter(description = "Max price appliance")
            @RequestParam(required = false) BigDecimal maxPrice,
            @Parameter(description = "Availability appliance")
            @RequestParam(required = false) Boolean availability

    ) {
        return modelApplianceService.getModelAppliance(applianceName, modelName, applianceType, serialNumber, color,
                size, minPrice, maxPrice, availability);
    }
}

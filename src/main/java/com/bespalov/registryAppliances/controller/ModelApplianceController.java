package com.bespalov.registryAppliances.controller;

import com.bespalov.registryAppliances.dto.ModelApplianceDto;
import com.bespalov.registryAppliances.service.ModelApplianceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<List<ModelApplianceDto>> filterModelAppliance(
            @RequestBody ModelApplianceDto modelApplianceDto) {
        return new ResponseEntity<>(modelApplianceService.filterModelAppliance(modelApplianceDto), HttpStatus.OK);
    }
}

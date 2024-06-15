package com.bespalov.registryAppliances.controller;

import com.bespalov.registryAppliances.dto.ApplianceDto;
import com.bespalov.registryAppliances.service.ApplianceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/appliances")
public class ApplianceController {
    private final ApplianceService applianceService;

    public ApplianceController(ApplianceService applianceService) {
        this.applianceService = applianceService;
    }

    @Operation(summary = "Create appliance",
            description = "A type of appliances is being created", tags = {"appliance"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Appliance has created"),
            @ApiResponse(responseCode = "400", description = "Not all fields are filled in"),
            @ApiResponse(responseCode = "500", description = "Not all DTO fields are filled in correctly")
    })
    @PostMapping
    public ResponseEntity<ApplianceDto> addAppliance(
            @RequestBody @Valid ApplianceDto applianceDto
    ) {
        return new ResponseEntity<>(applianceService.addAppliance(applianceDto), HttpStatus.OK);
    }

    @Operation(summary = "Get all appliances with sorting by alphabet", tags = {"appliance"})
    @GetMapping("/sort")
    public List<ApplianceDto> getAllAppliancesWithSortingByAlphabet(
            @Parameter(description = "Sort direction meaning")
            @RequestParam(defaultValue = "asc") String direction) {
        return applianceService.findAllAppliancesWithSortByAlphabet(direction);
    }
}

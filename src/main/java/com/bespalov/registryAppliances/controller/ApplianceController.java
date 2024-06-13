package com.bespalov.registryAppliances.controller;

import com.bespalov.registryAppliances.dto.ApplianceDto;
import com.bespalov.registryAppliances.service.ApplianceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
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
    @PostMapping("/products")
    public ResponseEntity<ApplianceDto> addAppliance(
            @RequestBody @Valid ApplianceDto applianceDto
    ) {
        return new ResponseEntity<>(applianceService.addAppliance(applianceDto), HttpStatus.OK);
    }
}

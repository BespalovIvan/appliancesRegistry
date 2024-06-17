package com.bespalov.registryAppliances.controller;

import com.bespalov.registryAppliances.dto.SmartphoneDto;
import com.bespalov.registryAppliances.service.SmartphoneService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/appliances")
public class SmartphoneController {
    private final SmartphoneService smartphoneService;

    public SmartphoneController(SmartphoneService smartphoneService) {
        this.smartphoneService = smartphoneService;
    }

    @Operation(summary = "Create a model of smartphone", description = "A model of a specific smartphone is being created",
            tags = {"smartphone"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Model has created"),
            @ApiResponse(responseCode = "400", description = "Not all fields are filled in"),
            @ApiResponse(responseCode = "500", description = "Not all DTO fields are filled in correctly")
    })
    @PostMapping("/smartphones")
    public ResponseEntity<HttpStatus> addSmartphone(
            @RequestBody @Valid SmartphoneDto smartphoneDto
    ) {
        smartphoneService.addSmartphone(smartphoneDto);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @Operation(summary = "Method getting and filtering smartphones", tags = {"smartphone"})
    @GetMapping("/smartphones")
    public ResponseEntity<List<SmartphoneDto>> filterSmartphones(@RequestBody SmartphoneDto smartphoneDto) {
        return new ResponseEntity<>(
                smartphoneService.filterSmartphones(smartphoneDto), HttpStatus.OK);

    }

}

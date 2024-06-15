package com.bespalov.registryAppliances.controller;

import com.bespalov.registryAppliances.dto.SmartphoneDto;
import com.bespalov.registryAppliances.service.SmartphoneService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

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

    @Operation(summary = "Method getting and filtering smartphones", tags = { "smartphone" })
    @GetMapping("/smartphones")
    public ResponseEntity<List<SmartphoneDto>> getAllAndFilterSmartphones(
            @Parameter(description = "The model name for filter")
            @RequestParam(required = false) String name,
            @Parameter(description = "The model serial number for filter")
            @RequestParam(required = false) String serialNumber,
            @Parameter(description = "The model color for filter")
            @RequestParam(required = false) String color,
            @Parameter(description = "The model size for filter")
            @RequestParam(required = false) String size,
            @Parameter(description = "The model min price  for filter")
            @RequestParam(required = false) BigDecimal minPrice,
            @Parameter(description = "The model max price for filter")
            @RequestParam(required = false) BigDecimal maxPrice,
            @Parameter(description = "The model memory for filter")
            @RequestParam(required = false) Integer memory,
            @Parameter(description = "The model count cameras for filter")
            @RequestParam(required = false) Integer countCameras,
            @Parameter(description = "The model availability for filter")
            @RequestParam(required = false) Boolean availability)
    {
        return new ResponseEntity<>(
                smartphoneService.filterSmartphones(name, serialNumber, color, size, minPrice, maxPrice, memory,
                        countCameras, availability),HttpStatus.OK);

    }
}

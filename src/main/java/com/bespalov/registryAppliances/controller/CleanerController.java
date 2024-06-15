package com.bespalov.registryAppliances.controller;

import com.bespalov.registryAppliances.dto.CleanerDto;
import com.bespalov.registryAppliances.service.CleanerService;
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

@RestController
@RequestMapping("/api/appliances")
public class CleanerController {
    private final CleanerService cleanerService;

    public CleanerController(CleanerService cleanerService) {
        this.cleanerService = cleanerService;
    }

    @Operation(summary = "Create a model of cleaner", description = "A model of a specific cleaner is being created",
            tags = {"cleaner"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Model has created"),
            @ApiResponse(responseCode = "400", description = "Not all fields are filled in"),
            @ApiResponse(responseCode = "500", description = "Not all DTO fields are filled in correctly")
    })
    @PostMapping("/cleaners")
    public ResponseEntity<HttpStatus> addCleaner(
            @RequestBody @Valid CleanerDto cleanerDto
    ) {
        cleanerService.addCleaner(cleanerDto);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @Operation(summary = "Method getting and filtering cleaners", tags = { "cleaner" })
    @GetMapping("/cleaners")
    public ResponseEntity<List<CleanerDto>> getAllAndFilterCleaners(
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
            @Parameter(description = "The model dust collector capacity for filter")
            @RequestParam(required = false) Integer dustCollectorCapacity,
            @Parameter(description = "The model count modes for filter")
            @RequestParam(required = false) Integer countOfModes,
            @Parameter(description = "The model availability for filter")
            @RequestParam(required = false) Boolean availability) {
        return new ResponseEntity<>(
                cleanerService.filterCleaner(name, serialNumber, color, size, minPrice, maxPrice, dustCollectorCapacity,
                        countOfModes, availability), HttpStatus.OK);

    }
}

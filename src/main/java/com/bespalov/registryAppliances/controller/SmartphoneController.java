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

    @GetMapping("/smartphones")
    public ResponseEntity<List<SmartphoneDto>> getAllAndFilterSmartphones(
            @Parameter(description = "The model name for filter", required = false)
            @RequestParam(required = false) String name,
            @Parameter(description = "The model's serial number for filter", required = false)
            @RequestParam(required = false) String serialNumber,
            @Parameter(description = "The model's color for filter", required = false)
            @RequestParam(required = false) String color,
            @Parameter(description = "The model's size for filter", required = false)
            @RequestParam(required = false) String size,
            @Parameter(description = "The model's min price  for filter", required = false)
            @RequestParam(required = false) BigDecimal minPrice,
            @Parameter(description = "The model's max price for filter", required = false)
            @RequestParam(required = false) BigDecimal maxPrice,
            @Parameter(description = "The model's memory for filter", required = false)
            @RequestParam(required = false) Integer memory,
            @Parameter(description = "The model's count cameras for filter", required = false)
            @RequestParam(required = false) Integer countCameras,
            @Parameter(description = "The model's availability for filter", required = false)
            @RequestParam(required = false) Boolean availability)
    {
        return new ResponseEntity<>(
                smartphoneService.filterSmartphones(name, serialNumber, color, size, minPrice, maxPrice, memory,
                        countCameras, availability),HttpStatus.OK);

    }
}

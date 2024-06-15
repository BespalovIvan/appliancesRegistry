package com.bespalov.registryAppliances.controller;

import com.bespalov.registryAppliances.dto.FridgeDto;
import com.bespalov.registryAppliances.service.FridgeService;
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
public class FridgeController {
    private final FridgeService fridgeService;

    public FridgeController(FridgeService fridgeService) {
        this.fridgeService = fridgeService;
    }

    @Operation(summary = "Create a model of fridge", description = "A model of a specific fridge is being created",
            tags = {"fridge"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Model has created"),
            @ApiResponse(responseCode = "400", description = "Not all fields are filled in"),
            @ApiResponse(responseCode = "500", description = "Not all DTO fields are filled in correctly")
    })
    @PostMapping("/fridges")
    public ResponseEntity<HttpStatus> addFridge(
            @RequestBody @Valid FridgeDto fridgeDto
    ) {
        fridgeService.addFridge(fridgeDto);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @Operation(summary = "Method getting and filtering fridges", tags = {"fridge"})
    @GetMapping("/fridges")
    public ResponseEntity<List<FridgeDto>> getAllAndFilterFridges(
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
            @Parameter(description = "The model count doors for filter")
            @RequestParam(required = false) Integer countOfDoors,
            @Parameter(description = "The model type of compressor for filter")
            @RequestParam(required = false) String typeOfCompressor,
            @Parameter(description = "The model availability for filter")
            @RequestParam(required = false) Boolean availability) {
        return new ResponseEntity<>(
                fridgeService.filterFridge(name, serialNumber, color, size, minPrice, maxPrice, countOfDoors,
                        typeOfCompressor, availability), HttpStatus.OK);

    }
}

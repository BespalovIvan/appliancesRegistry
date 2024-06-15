package com.bespalov.registryAppliances.controller;

import com.bespalov.registryAppliances.dto.TvDto;
import com.bespalov.registryAppliances.service.TvService;
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
public class TvController {
    private final TvService tvService;

    public TvController(TvService tvService) {
        this.tvService = tvService;
    }

    @Operation(summary = "Create a model of tv", description = "A model of a specific tv is being created",
            tags = {"tv"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Model has created"),
            @ApiResponse(responseCode = "400", description = "Not all fields are filled in"),
            @ApiResponse(responseCode = "500", description = "Not all DTO fields are filled in correctly")
    })
    @PostMapping("/tv")
    public ResponseEntity<HttpStatus> addTv(
            @RequestBody @Valid TvDto tvDto
    ) {
        tvService.addTv(tvDto);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @Operation(summary = "Method getting and filtering tv", tags = {"tv"})
    @GetMapping("/tv")
    public ResponseEntity<List<TvDto>> getAllAndFilterTv(
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
            @Parameter(description = "The model category for filter")
            @RequestParam(required = false) String category,
            @Parameter(description = "The model technology for filter")
            @RequestParam(required = false) String technology,
            @Parameter(description = "The model availability for filter")
            @RequestParam(required = false) Boolean availability) {
        return new ResponseEntity<>(
                tvService.filterTv(name, serialNumber, color, size, minPrice, maxPrice, category,
                        technology, availability), HttpStatus.OK);

    }
}

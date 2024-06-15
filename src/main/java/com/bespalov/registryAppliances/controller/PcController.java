package com.bespalov.registryAppliances.controller;

import com.bespalov.registryAppliances.dto.PcDto;
import com.bespalov.registryAppliances.service.PcService;
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
public class PcController {
    private final PcService pcService;

    public PcController(PcService pcService) {
        this.pcService = pcService;
    }

    @Operation(summary = "Create a model of pc", description = "A model of a specific pc is being created",
            tags = {"pc"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Model has created"),
            @ApiResponse(responseCode = "400", description = "Not all fields are filled in"),
            @ApiResponse(responseCode = "500", description = "Not all DTO fields are filled in correctly")
    })
    @PostMapping("/pc")
    public ResponseEntity<HttpStatus> addPc(
            @RequestBody @Valid PcDto pcDto
    ) {
        pcService.addPc(pcDto);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @Operation(summary = "Method getting and filtering pc", tags = {"pc"})
    @GetMapping("/pc")
    public ResponseEntity<List<PcDto>> getAllAndFilterPc(
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
            @Parameter(description = "The model processor for filter")
            @RequestParam(required = false) String processor,
            @Parameter(description = "The model availability for filter")
            @RequestParam(required = false) Boolean availability) {
        return new ResponseEntity<>(
                pcService.filterPc(name, serialNumber, color, size, minPrice, maxPrice, category,
                        processor, availability), HttpStatus.OK);

    }
}

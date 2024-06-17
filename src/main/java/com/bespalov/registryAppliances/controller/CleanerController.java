package com.bespalov.registryAppliances.controller;

import com.bespalov.registryAppliances.dto.CleanerDto;
import com.bespalov.registryAppliances.service.CleanerService;
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

    @Operation(summary = "Method getting and filtering cleaners", tags = {"cleaner"})
    @GetMapping("/cleaners")
    public ResponseEntity<List<CleanerDto>> filterCleaners(@RequestBody CleanerDto cleanerDto) {
        return new ResponseEntity<>(
                cleanerService.filterCleaner(cleanerDto), HttpStatus.OK);

    }
}

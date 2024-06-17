package com.bespalov.registryAppliances.controller;

import com.bespalov.registryAppliances.dto.FridgeDto;
import com.bespalov.registryAppliances.service.FridgeService;
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
    public ResponseEntity<List<FridgeDto>> getAllAndFilterFridges(@RequestBody FridgeDto fridgeDto) {
        return new ResponseEntity<>(
                fridgeService.filterFridge(fridgeDto), HttpStatus.OK);

    }
}

package com.ezra.elevator.controller;

import com.ezra.elevator.dto.AddElevatorRequest;
import com.ezra.elevator.dto.UpdateElevatorInfoRequest;
import com.ezra.elevator.model.ElevatorInfo;
import com.ezra.elevator.service.ElevatorInfoService;
import com.ezra.elevator.service.ElevatorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/elevators/info")
@Slf4j
@RequiredArgsConstructor
public class ElevatorInfoController {

    @Autowired
    ElevatorInfoService elevatorInfoService;
    @Operation(summary = "Add Elevator information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Elevator information Added Successfully",
                    content = {@Content(mediaType = "application/json",schema = @Schema(implementation = ElevatorInfo.class))}),
            @ApiResponse(responseCode = "401",description = "Unauthorized user",content = @Content),
            @ApiResponse(responseCode = "404",description = "Elevator Information not found",content = @Content),
            @ApiResponse(responseCode = "400",description = "Invalid elevatorInfo ",content = @Content)})
    @PostMapping("new")
    public ResponseEntity<?> addOrUpdateElevatorInfo(@RequestBody UpdateElevatorInfoRequest updateElevatorInfoRequest){
        return elevatorInfoService.createOrUpdateElevatorInfo(updateElevatorInfoRequest);

    }


    @Operation(summary = "List All Elevators' Information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "302", description = "Listed all elevators' information",
                    content = {@Content(mediaType = "application/json",schema = @Schema(implementation = ElevatorInfo.class))}),
            @ApiResponse(responseCode = "401",description = "Unauthorized user",content = @Content),
            @ApiResponse(responseCode = "404",description = "Elevator Information not found",content = @Content),
            @ApiResponse(responseCode = "400",description = "Invalid request",content = @Content)})
    @GetMapping("list")
    public ResponseEntity<?> findAll(){
        return elevatorInfoService.findAll();

    }
    //Admin only
    @Operation(summary = "Find an Elevator information by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "302", description = "Find elevator information by id",
                    content = {@Content(mediaType = "application/json",schema = @Schema(implementation = ElevatorInfo.class))}),
            @ApiResponse(responseCode = "401",description = "Unauthorized user",content = @Content),
            @ApiResponse(responseCode = "404",description = "Elevator Information not found",content = @Content),
            @ApiResponse(responseCode = "400",description = "Invalid elevatorInfo id",content = @Content)})
    @GetMapping("find/{id}")
    public ResponseEntity<?> findById(@PathVariable long id){
        return elevatorInfoService.findById(id);

    }

}

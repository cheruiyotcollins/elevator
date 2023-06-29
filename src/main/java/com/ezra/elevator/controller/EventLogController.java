package com.ezra.elevator.controller;

import com.ezra.elevator.dto.AddElevatorRequest;
import com.ezra.elevator.model.ElevatorInfo;
import com.ezra.elevator.service.ElevatorService;
import com.ezra.elevator.service.EventLogService;
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
@RequestMapping(value="/events/log")
@Slf4j
@RequiredArgsConstructor
public class EventLogController {

    private final EventLogService eventLogService;


    @Operation(summary = "List All  Elevator Info Logs")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Listed All Elevator Info Logs",
                    content = {@Content(mediaType = "application/json",schema = @Schema(implementation = ElevatorInfo.class))}),
            @ApiResponse(responseCode = "401",description = "Unauthorized user",content = @Content),
            @ApiResponse(responseCode = "404",description = "No Record Found",content = @Content),
            @ApiResponse(responseCode = "400",description = "Invalid request ",content = @Content)})
    @GetMapping("list")
    public ResponseEntity<?> findAll(){
        return eventLogService.findAllEvents();

    }
    //Admin only

    @Operation(summary = "List All  Elevator Info Logs Per Elevator")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "302", description = "Listed All Elevator Info Logs",
                    content = {@Content(mediaType = "application/json",schema = @Schema(implementation = ElevatorInfo.class))}),
            @ApiResponse(responseCode = "401",description = "Unauthorized user",content = @Content),
            @ApiResponse(responseCode = "404",description = "No Record Found",content = @Content),
            @ApiResponse(responseCode = "400",description = "Invalid request ",content = @Content)})
        @GetMapping("find/elevator/{elevatorId}")
    public ResponseEntity<?> findByElevatorId(@PathVariable long elevatorId){
        return eventLogService.findAllEventsPerElevator(elevatorId);

    }
    @Operation(summary = "List All  Elevator Info Logs")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "302", description = "Listed All Elevator Info Logs",
                    content = {@Content(mediaType = "application/json",schema = @Schema(implementation = ElevatorInfo.class))}),
            @ApiResponse(responseCode = "401",description = "Unauthorized user",content = @Content),
            @ApiResponse(responseCode = "404",description = "No Record Found",content = @Content),
            @ApiResponse(responseCode = "400",description = "Invalid request ",content = @Content)})
    @GetMapping("find/{logId}")
    public ResponseEntity<?> findByLogId(@PathVariable long logId){
        return eventLogService.findAllEventsPerLogId(logId);

    }
}

package com.ezra.elevator.controller;

import com.ezra.elevator.dto.AddElevatorRequest;
import com.ezra.elevator.model.Elevator;
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
@RequestMapping(value="/elevators/")
@Slf4j
@RequiredArgsConstructor
public class ElevatorController {
    private final ElevatorService elevatorService;

    @Operation(summary = "Add New Elevator")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Added Elevator",
                    content = {@Content(mediaType = "application/json",schema = @Schema(implementation = Elevator.class))}),
            @ApiResponse(responseCode = "401",description = "Unauthorized user",content = @Content),
            @ApiResponse(responseCode = "404",description = "not found",content = @Content),
            @ApiResponse(responseCode = "400",description = "Bad request",content = @Content)})
    @PostMapping("new")
    public ResponseEntity<?> addElevator(@RequestBody AddElevatorRequest addElevatorRequest){
                return elevatorService.addElevator(addElevatorRequest);

    }
    @Operation(summary = "Update Elevator")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Add Elevator",
                    content = {@Content(mediaType = "application/json",schema = @Schema(implementation = Elevator.class))}),
            @ApiResponse(responseCode = "401",description = "Unauthorized user",content = @Content),
            @ApiResponse(responseCode = "404",description = "Elevator not found",content = @Content),
            @ApiResponse(responseCode = "400",description = "Bad request",content = @Content)})
    @PostMapping("update/{id}")
    public ResponseEntity<?> updateElevator(@PathVariable long id, @RequestBody AddElevatorRequest addElevatorRequest){
        return elevatorService.updateElevator(id,addElevatorRequest);

    }
    @Operation(summary = "List all employees")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listed All Elevators",
                    content = {@Content(mediaType = "application/json",schema = @Schema(implementation = Elevator.class))}),
            @ApiResponse(responseCode = "401",description = "Unauthorized user",content = @Content),
            @ApiResponse(responseCode = "404",description = "Elevator not found",content = @Content),
            @ApiResponse(responseCode = "302",description = "No EleVator Found",content = @Content)})

     @GetMapping("list")
    public ResponseEntity<?> findAll(){
        return elevatorService.findAll();

    }
    //Admin only
    @Operation(summary = "Find  an elevator by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "302", description = "got elevator by id",
                    content = {@Content(mediaType = "application/json",schema = @Schema(implementation = Elevator.class))}),
            @ApiResponse(responseCode = "401",description = "Unauthorized user",content = @Content),
            @ApiResponse(responseCode = "404",description = "Elevator not found",content = @Content),
            @ApiResponse(responseCode = "400",description = "Invalid Elevator id",content = @Content)})
    @GetMapping("find/{id}")
    public ResponseEntity<?> findById(@PathVariable long id){
        return elevatorService.findById(id);

    }
    @Operation(summary = "Delete an elevator by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deleted an Elevator",
                    content = {@Content(mediaType = "application/json",schema = @Schema(implementation = Elevator.class))}),
            @ApiResponse(responseCode = "401",description = "Unauthorized user",content = @Content),
            @ApiResponse(responseCode = "404",description = "Elevator not found",content = @Content),
            @ApiResponse(responseCode = "400",description = "Invalid elevator id",content = @Content)})
    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable long id){
        return elevatorService.deleteById(id);

    }
}

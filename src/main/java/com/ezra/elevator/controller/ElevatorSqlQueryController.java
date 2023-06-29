package com.ezra.elevator.controller;

import com.ezra.elevator.dto.ResponseDto;
import com.ezra.elevator.model.ElevatorInfo;
import com.ezra.elevator.service.JpaSqlQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/queries/")
@Slf4j
@RequiredArgsConstructor
public class ElevatorSqlQueryController {

    private final JpaSqlQueryService jpaSqlQueryService;

    @Operation(summary = "List All  Jpa Queries")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Listed All Jpa Queries",
                    content = {@Content(mediaType = "application/json",schema = @Schema(implementation = ElevatorInfo.class))}),
            @ApiResponse(responseCode = "404",description = "No Record Found",content = @Content),
            @ApiResponse(responseCode = "400",description = "Invalid request ",content = @Content)})

    @GetMapping("list")
    public ResponseEntity<ResponseDto> findAllSqlQueries(){
        return jpaSqlQueryService.findAllSQLQueries();

    }
    @Operation(summary = "Find Jpa SQL Query by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Query Found",
                    content = {@Content(mediaType = "application/json",schema = @Schema(implementation = ElevatorInfo.class))}),
            @ApiResponse(responseCode = "404",description = "No Query Found",content = @Content),
            @ApiResponse(responseCode = "400",description = "Invalid request ",content = @Content)})
    @GetMapping("find/{queryId}")
    public ResponseEntity<ResponseDto> findSQLQueryById(@PathVariable long queryId ){
        return jpaSqlQueryService.findSQLQueriesById(queryId);
    }
}

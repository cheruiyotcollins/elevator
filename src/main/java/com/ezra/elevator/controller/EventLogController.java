package com.ezra.elevator.controller;

import com.ezra.elevator.dto.AddElevatorRequest;
import com.ezra.elevator.service.ElevatorService;
import com.ezra.elevator.service.EventLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/events/log")
public class EventLogController {

    @Autowired
    EventLogService eventLogService;


    @GetMapping("list")
    public ResponseEntity<?> findAll(){
        return eventLogService.findAllEvents();

    }
    //Admin only
    @GetMapping("find/elevator/{elevatorId}")
    public ResponseEntity<?> findByElevatorId(@PathVariable long elevatorId){
        return eventLogService.findAllEventsPerElevator(elevatorId);

    }
    @GetMapping("find/{logId}")
    public ResponseEntity<?> findByLogId(@PathVariable long logId){
        return eventLogService.findAllEventsPerLogId(logId);

    }
}

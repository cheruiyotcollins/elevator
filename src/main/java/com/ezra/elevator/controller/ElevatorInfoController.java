package com.ezra.elevator.controller;

import com.ezra.elevator.dto.AddElevatorRequest;
import com.ezra.elevator.dto.UpdateElevatorInfoRequest;
import com.ezra.elevator.service.ElevatorInfoService;
import com.ezra.elevator.service.ElevatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/elevators/info")
public class ElevatorInfoController {

    @Autowired
    ElevatorInfoService elevatorInfoService;
    @PostMapping("new")
    public ResponseEntity<?> addOrUpdateElevatorInfo(@RequestBody UpdateElevatorInfoRequest updateElevatorInfoRequest){
        return elevatorInfoService.createOrUpdateElevatorInfo(updateElevatorInfoRequest);

    }


    @GetMapping("list")
    public ResponseEntity<?> findAll(){
        return elevatorInfoService.findAll();

    }
    //Admin only
    @GetMapping("find/{id}")
    public ResponseEntity<?> findById(@PathVariable long id){
        return elevatorInfoService.findById(id);

    }

}

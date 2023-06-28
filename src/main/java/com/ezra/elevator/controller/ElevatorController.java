package com.ezra.elevator.controller;

import com.ezra.elevator.dto.AddElevatorRequest;
import com.ezra.elevator.service.ElevatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/elevators/")
public class ElevatorController {
    @Autowired
    ElevatorService elevatorService;
    @PostMapping("new")
    public ResponseEntity<?> addElevator(@RequestBody AddElevatorRequest addElevatorRequest){
                return elevatorService.addElevator(addElevatorRequest);

    }
    @PostMapping("update/{id}")
    public ResponseEntity<?> updateElevator(@PathVariable long id, @RequestBody AddElevatorRequest addElevatorRequest){
        return elevatorService.updateElevator(id,addElevatorRequest);

    }

     @GetMapping("list")
    public ResponseEntity<?> findAll(){
        return elevatorService.findAll();

    }
    //Admin only
    @GetMapping("find/{id}")
    public ResponseEntity<?> findById(@PathVariable long id){
        return elevatorService.findById(id);

    }
    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable long id){
        return elevatorService.deleteById(id);

    }
}

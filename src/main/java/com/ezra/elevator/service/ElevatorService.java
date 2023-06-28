package com.ezra.elevator.service;

import com.ezra.elevator.dto.AddElevatorRequest;
import com.ezra.elevator.dto.GeneralResponse;
import com.ezra.elevator.model.Elevator;
import com.ezra.elevator.model.ElevatorInfo;
import com.ezra.elevator.repository.ElevatorInfoRepository;
import com.ezra.elevator.repository.ElevatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ElevatorService {
    @Autowired
    ElevatorRepository elevatorRepository;

    @Autowired
    ElevatorInfoRepository elevatorInfoRepository;

    GeneralResponse generalResponse=new GeneralResponse();

    public ResponseEntity<?> addElevator(AddElevatorRequest addElevatorRequest){

        try{

//            calling setUpElevator method to set up elevator using addElevatorRequest
            Elevator elevator=setUpElevator(addElevatorRequest);
              //saving elevator
               Elevator newElevator= elevatorRepository.save(elevator);
            ElevatorInfo elevatorInfo=new ElevatorInfo();
            elevatorInfo.setDirection("N/A");
            elevatorInfo.setState("STOPPED");
            elevatorInfo.setPostionFloorNo(0);
            elevatorInfo.setEventTime(LocalDateTime.now());
            elevatorInfo.setElevator(newElevator);
            elevatorInfoRepository.save(elevatorInfo);
            generalResponse.setStatus(HttpStatus.CREATED);
            generalResponse.setDescription("Elevator added Successfully");
                return new ResponseEntity<>(generalResponse, HttpStatus.CREATED);




        }catch(Exception e){
            generalResponse.setStatus(HttpStatus.BAD_REQUEST);
            generalResponse.setDescription("Something went wrong, elevator not added");

            return new ResponseEntity<>(generalResponse, HttpStatus.BAD_REQUEST);
        }

    }
    public ResponseEntity<?> updateElevator(long id,AddElevatorRequest addElevatorRequest){
        try{
            //calling setUpElevator method to set up elevator using addElevatorRequest
            Elevator elevator=setUpElevator(addElevatorRequest);
            //setting up id
            elevator.setId(id);
            //saving elevator
            elevatorRepository.save(elevator);
            generalResponse.setStatus(HttpStatus.CREATED);
            generalResponse.setDescription("Elevator updated Successfully");
            return new ResponseEntity<>(generalResponse, HttpStatus.CREATED);




        }catch(Exception e){
            generalResponse.setStatus(HttpStatus.BAD_REQUEST);
            generalResponse.setDescription("Something went wrong, elevator not added");

            return new ResponseEntity<>(generalResponse, HttpStatus.BAD_REQUEST);
        }

    }
    public Elevator setUpElevator(AddElevatorRequest addElevatorRequest){
        Elevator elevator=new Elevator();
        elevator.setName(addElevatorRequest.getName());
        elevator.setMaxLoadKg(addElevatorRequest.getMaxLoadKg());
        elevator.setMaxNoOfPeople(addElevatorRequest.getMaxNoOfPeople());
        elevator.setManufacturer(addElevatorRequest.getManufacturer());
        return  elevator;
    }
    public ResponseEntity<?> findAll(){


        return new ResponseEntity<>(elevatorRepository.findAll(), HttpStatus.FOUND);


    }
    public ResponseEntity<?> findById(long id){
        if(elevatorRepository.existsById(id)){
            return new ResponseEntity<>(elevatorRepository.findById(id).get(), HttpStatus.FOUND);
        }
            generalResponse.setStatus(HttpStatus.NOT_FOUND);
            generalResponse.setDescription("elevator with provided id not found");

            return new ResponseEntity<>(generalResponse, HttpStatus.NOT_FOUND);


    }
    public ResponseEntity<?> deleteById(long id){
        if(elevatorRepository.existsById(id)){
            elevatorRepository.deleteById(id);
            generalResponse.setStatus(HttpStatus.OK);
            generalResponse.setDescription("elevator deleted successfully");
            return new ResponseEntity<>(generalResponse, HttpStatus.OK);
        }
        generalResponse.setStatus(HttpStatus.NOT_FOUND);
        generalResponse.setDescription("elevator with provided id not found");

        return new ResponseEntity<>(generalResponse, HttpStatus.NOT_FOUND);


    }

}

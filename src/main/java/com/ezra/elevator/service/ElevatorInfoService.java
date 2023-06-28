package com.ezra.elevator.service;

import com.ezra.elevator.dto.GeneralResponse;
import com.ezra.elevator.dto.UpdateElevatorInfoRequest;
import com.ezra.elevator.model.ElevatorInfo;
import com.ezra.elevator.model.EventLog;
import com.ezra.elevator.repository.ElevatorInfoRepository;
import com.ezra.elevator.repository.ElevatorRepository;
import com.ezra.elevator.repository.EventLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
public class ElevatorInfoService {
    @Autowired
    ElevatorInfoRepository elevatorInfoRepository;
    @Autowired
    ElevatorRepository elevatorRepository;
    @Autowired
    EventLogService eventLogService;
     GeneralResponse generalResponse=new GeneralResponse();
    public ResponseEntity<?> createOrUpdateElevatorInfo(UpdateElevatorInfoRequest updateElevatorInfoRequest){
        if(!elevatorRepository.existsById(updateElevatorInfoRequest.getElevator_id())){
            generalResponse.setStatus(HttpStatus.NOT_FOUND);
            generalResponse.setDescription("No elevator found with provided id");
            return new ResponseEntity<>(generalResponse,HttpStatus.NOT_FOUND);
        }
        if(elevatorInfoRepository.existsById(updateElevatorInfoRequest.getElevator_id())){
            ElevatorInfo elevatorInfo= elevatorInfoRepository.findById(updateElevatorInfoRequest.getElevator_id()).get();
            //Creating new event log
            EventLog eventLog=new EventLog();
            eventLog.setElevatorInfo(elevatorInfo);
            eventLog.setLogTime(LocalDateTime.now());
            //calling event log service to save  current even before updating
            eventLogService.addEventLog(eventLog);
            //setting up and updating new elevator info
            elevatorInfo.setDirection(updateElevatorInfoRequest.getDirection());
            elevatorInfo.setState(updateElevatorInfoRequest.getState());
            elevatorInfo.setPlace(updateElevatorInfoRequest.getPlace());
            elevatorInfo.setEventTime(LocalDateTime.now());
            elevatorInfoRepository.save(elevatorInfo);
            generalResponse.setStatus(HttpStatus.CREATED);
            generalResponse.setDescription("Elevator Information updated successfully");
            return new ResponseEntity<>(generalResponse,HttpStatus.CREATED);

        }

        ElevatorInfo elevatorInfo=new ElevatorInfo();
        elevatorInfo.setDirection(updateElevatorInfoRequest.getDirection());
        elevatorInfo.setState(updateElevatorInfoRequest.getState());
        elevatorInfo.setPlace(updateElevatorInfoRequest.getPlace());
        elevatorInfo.setEventTime(LocalDateTime.now());
        elevatorInfo.setElevator(elevatorRepository.findById(updateElevatorInfoRequest.getElevator_id()).get());
        elevatorInfoRepository.save(elevatorInfo);

        generalResponse.setStatus(HttpStatus.CREATED);
        generalResponse.setDescription("Elevator Information created successfully");
        return new ResponseEntity<>(generalResponse,HttpStatus.CREATED);
        }

    public ResponseEntity<?> findAll(){


        return new ResponseEntity<>(elevatorInfoRepository.findAll(), HttpStatus.FOUND);


    }
    public ResponseEntity<?> findById(long id){
        if(elevatorInfoRepository.existsById(id)){
            return new ResponseEntity<>(elevatorInfoRepository.findById(id).get(), HttpStatus.FOUND);
        }
        generalResponse.setStatus(HttpStatus.NOT_FOUND);
        generalResponse.setDescription("elevator info with provided id not found");

        return new ResponseEntity<>(generalResponse, HttpStatus.NOT_FOUND);


    }
    public ResponseEntity<?> deleteById(long id){
        if(elevatorInfoRepository.existsById(id)){
            elevatorInfoRepository.deleteById(id);
            generalResponse.setStatus(HttpStatus.ACCEPTED);
            generalResponse.setDescription("elevator info deleted successfully");
            return new ResponseEntity<>(generalResponse, HttpStatus.ACCEPTED);
        }
        generalResponse.setStatus(HttpStatus.NOT_FOUND);
        generalResponse.setDescription("elevator  info with provided id not found");

        return new ResponseEntity<>(generalResponse, HttpStatus.NOT_FOUND);


    }

}

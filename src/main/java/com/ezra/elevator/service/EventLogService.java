package com.ezra.elevator.service;

import com.ezra.elevator.dto.GeneralResponse;
import com.ezra.elevator.model.EventLog;
import com.ezra.elevator.repository.ElevatorInfoRepository;
import com.ezra.elevator.repository.ElevatorRepository;
import com.ezra.elevator.repository.EventLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class EventLogService {
    @Autowired
    EventLogRepository eventLogRepository;
    @Autowired
    ElevatorInfoRepository elevatorInfoRepository;
    @Autowired
    ElevatorRepository elevatorRepository;
    GeneralResponse generalResponse= new GeneralResponse();

    public void addEventLog(EventLog  eventLog){
        eventLogRepository.save(eventLog);
    }
    public ResponseEntity<?> findAllEvents(){
        return new ResponseEntity<>(eventLogRepository.findAll(), HttpStatus.ACCEPTED);
    }
    public ResponseEntity<?> findAllEventsPerElevator(long elevatorId){
        return new ResponseEntity<>(eventLogRepository.findByElevatorInfo(elevatorInfoRepository.findByElevator(elevatorRepository.findById(elevatorId).get()).get()), HttpStatus.ACCEPTED);
    }
    public ResponseEntity<?> findAllEventsPerLogId(long id){
        if(!eventLogRepository.existsById(id)){
            generalResponse.setStatus(HttpStatus.NOT_FOUND);
            generalResponse.setDescription("Event log with provided id not found");
            return new ResponseEntity<>(generalResponse,HttpStatus.NOT_FOUND);
        }


        return new ResponseEntity<>(eventLogRepository.findById(id), HttpStatus.ACCEPTED);
    }

}

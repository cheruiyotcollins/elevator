package com.ezra.elevator.service;

import com.ezra.elevator.dto.ResponseDto;
import com.ezra.elevator.model.Elevator;
import com.ezra.elevator.model.ElevatorInfo;
import com.ezra.elevator.model.EventLog;
import com.ezra.elevator.repository.ElevatorInfoRepository;
import com.ezra.elevator.repository.ElevatorRepository;
import com.ezra.elevator.repository.EventLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class EventLogService {


    private final ElevatorInfoRepository elevatorInfoRepository;

    private final ElevatorRepository elevatorRepository;

    private final EventLogRepository eventLogRepository;

    ResponseDto responseDto = new ResponseDto();

    public void addEventLog(EventLog  eventLog){
        eventLogRepository.save(eventLog);
    }
    public ResponseEntity<?> findAllEvents(){
        return new ResponseEntity<>(eventLogRepository.findAll(), HttpStatus.FOUND);
    }
    public ResponseEntity<?> findAllEventsPerElevator(long elevatorId){
        if(!elevatorRepository.existsById(elevatorId)){
            responseDto.setStatus(HttpStatus.FOUND);
            responseDto.setDescription("Elevator with that id not found");
            return new ResponseEntity<>(responseDto,HttpStatus.FOUND);
        }
        Elevator elevator=elevatorRepository.findById(elevatorId).get();

        ElevatorInfo elevatorInfo=elevatorInfoRepository.findByElevator(elevator).get();

        List<EventLog> eventLog=eventLogRepository.findByElevatorInfo(elevatorInfo);

        return new ResponseEntity<>(eventLog, HttpStatus.FOUND);
    }
    public ResponseEntity<?> findAllEventsPerLogId(long id){
        if(!eventLogRepository.existsById(id)){
            responseDto.setStatus(HttpStatus.NOT_FOUND);
            responseDto.setDescription("Event log with provided id not found");
            return new ResponseEntity<>(responseDto,HttpStatus.NOT_FOUND);
        }


        return new ResponseEntity<>(eventLogRepository.findById(id), HttpStatus.ACCEPTED);
    }

}

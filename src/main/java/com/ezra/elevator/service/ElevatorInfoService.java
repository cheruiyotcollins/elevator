package com.ezra.elevator.service;

import com.ezra.elevator.dto.ResponseDto;
import com.ezra.elevator.dto.UpdateElevatorInfoRequest;
import com.ezra.elevator.model.ElevatorInfo;
import com.ezra.elevator.model.EventLog;
import com.ezra.elevator.model.JpaSqlQuery;
import com.ezra.elevator.repository.ElevatorInfoRepository;
import com.ezra.elevator.repository.ElevatorRepository;
import com.ezra.elevator.repository.EventLogRepository;
import com.ezra.elevator.repository.JpaSqlQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class ElevatorInfoService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ElevatorInfoService.class);
    private final ElevatorInfoRepository elevatorInfoRepository;
    private final ElevatorRepository elevatorRepository;
    private final EventLogRepository eventLogRepository;
    @Autowired
    JpaSqlQueryService jpaSqlQueryService;
     ResponseDto responseDto =new ResponseDto();

    @Value("${building-floors}")
    private int noOfBuildingFloors;
    public ResponseEntity<?> createOrUpdateElevatorInfo(UpdateElevatorInfoRequest updateElevatorInfoRequest){
       log.info("::::::Checking caller position and destination if its within building floors");
       if(updateElevatorInfoRequest.getCallerPosition()>noOfBuildingFloors||updateElevatorInfoRequest.getDestination()>noOfBuildingFloors){
           responseDto.setStatus(HttpStatus.NOT_ACCEPTABLE);
           responseDto.setDescription("Caller's current position and destination should not be more than number of configured floors in a building");
           return new ResponseEntity<>(responseDto,HttpStatus.NOT_ACCEPTABLE);
       }
        if(!elevatorRepository.existsById(updateElevatorInfoRequest.getElevatorId())){
            responseDto.setStatus(HttpStatus.NOT_FOUND);
            responseDto.setDescription("No elevator found with provided id");
            return new ResponseEntity<>(responseDto,HttpStatus.NOT_FOUND);
        }
        if(elevatorInfoRepository.existsById(updateElevatorInfoRequest.getElevatorId())){
            ElevatorInfo elevatorInfo= elevatorInfoRepository.findByElevator(elevatorRepository.findById(updateElevatorInfoRequest.getElevatorId()).get()).get();
            //Creating new event log
            EventLog eventLog=new EventLog();
            eventLog.setElevatorInfo(elevatorInfo);
            eventLog.setLogTime(LocalDateTime.now());
            //calling event log service to save  current even before updating
            eventLogRepository.save(eventLog);
            //Calling the Elevator from its current position to the caller's position

            if(elevatorInfo.getPostionFloorNo()!=updateElevatorInfoRequest.getCallerPosition()){
                //Calls Elevator if not in same floor
                callElevator(updateElevatorInfoRequest,elevatorInfo);
                //open door when Elevator Arrives
                openDoor(elevatorInfo);
                //take caller to destination
                takeCallerToDestination(updateElevatorInfoRequest,elevatorInfo);


            }

            if(elevatorInfo.getPostionFloorNo()==updateElevatorInfoRequest.getCallerPosition()){
                //if elevator's is in the same floor as the caller
                openDoor(elevatorInfo);
                //take caller to destination
                takeCallerToDestination(updateElevatorInfoRequest,elevatorInfo);
            }


            responseDto.setStatus(HttpStatus.CREATED);
            responseDto.setDescription("Elevator Information updated successfully");
            return new ResponseEntity<>(responseDto,HttpStatus.CREATED);

        }

        ElevatorInfo elevatorInfo=new ElevatorInfo();
        elevatorInfo.setDirection("N/A");
        elevatorInfo.setState("STOPPED");
        elevatorInfo.setPostionFloorNo(0);
        elevatorInfo.setEventTime(LocalDateTime.now());
        elevatorInfo.setElevator(elevatorRepository.findById(updateElevatorInfoRequest.getElevatorId()).get());
        elevatorInfoRepository.save(elevatorInfo);

        responseDto.setStatus(HttpStatus.CREATED);
        responseDto.setDescription("Elevator Information created successfully");
        return new ResponseEntity<>(responseDto,HttpStatus.CREATED);
        }
        public void openDoor(ElevatorInfo elevatorInfo){
            try{
                LOGGER.info(":::::::::::Elevator Door Opening:::::::::::");
                elevatorInfo.setState("OPENING");
                elevatorInfo.setDirection("STOPPED");
                elevatorInfoRepository.save(elevatorInfo);
                //Adding event to logs
                EventLog eventLog=new EventLog();
                eventLog.setElevatorInfo(elevatorInfo);
                eventLog.setLogTime(LocalDateTime.now());
                eventLogRepository.save(eventLog);

                Thread.sleep(2000);
            }catch(InterruptedException e){
                System.out.println(e);}

        }
    public void callElevator(UpdateElevatorInfoRequest updateElevatorInfoRequest,ElevatorInfo elevatorInfo){
        if(elevatorInfo.getPostionFloorNo()< updateElevatorInfoRequest.getCallerPosition()){
            LOGGER.info(":::::::::::Elevator Moving Up ^:::::::");
            elevatorInfo.setState("MOVING");
            elevatorInfo.setDirection("UP");
        }else{
            LOGGER.info(":::::::::::Elevator Moving Down v:::::::");
            elevatorInfo.setState("MOVING");
            elevatorInfo.setDirection("DOWN");
        }
        try{

            elevatorInfoRepository.save(elevatorInfo);
            //Adding event to logs
            EventLog eventLog=new EventLog();
            eventLog.setElevatorInfo(elevatorInfo);
            eventLog.setLogTime(LocalDateTime.now());
            eventLogRepository.save(eventLog);
            Thread.sleep((Math.abs(updateElevatorInfoRequest.getCallerPosition()-elevatorInfo.getPostionFloorNo())*1000));
        }catch(InterruptedException e){System.out.println(e);}

    }
    public void takeCallerToDestination(UpdateElevatorInfoRequest updateElevatorInfoRequest,ElevatorInfo elevatorInfo){
        if(updateElevatorInfoRequest.getDestination()> updateElevatorInfoRequest.getCallerPosition()){
            LOGGER.info(":::::::::::Elevator Moving Up ^:::::::");
            elevatorInfo.setState("MOVING");
            elevatorInfo.setDirection("UP");
        }else{
            LOGGER.info(":::::::::::Elevator Moving Down v:::::::");
            elevatorInfo.setState("MOVING");
            elevatorInfo.setDirection("DOWN");
        }
       try{

            elevatorInfoRepository.save(elevatorInfo);
            Thread.sleep((Math.abs(updateElevatorInfoRequest.getCallerPosition()- updateElevatorInfoRequest.getDestination())*1000));
           elevatorInfo.setDirection("N/A");
           elevatorInfo.setState("STOPPED");
           elevatorInfo.setPostionFloorNo(updateElevatorInfoRequest.getDestination());
           elevatorInfo.setEventTime(LocalDateTime.now());
           elevatorInfoRepository.save(elevatorInfo);

        }catch(InterruptedException e){System.out.println(e);}

    }



    public ResponseEntity<?> findAll(){

        jpaSqlQueryService.saveJpaQuery(LocalDateTime.now(),"ElevatorInfoService.Class, findAll Method","select * from elevator_info");


        return new ResponseEntity<>(elevatorInfoRepository.findAll(), HttpStatus.FOUND);



    }
    public ResponseEntity<?> findById(long id){
        if(elevatorInfoRepository.existsById(id)){

            jpaSqlQueryService.saveJpaQuery(LocalDateTime.now(),"ElevatorInfoService.Class, findById Method","select * from elevator_info where id="+id);

            return new ResponseEntity<>(elevatorInfoRepository.findById(id).get(), HttpStatus.FOUND);
        }
        responseDto.setStatus(HttpStatus.NOT_FOUND);
        responseDto.setDescription("elevator info with provided id not found");

        return new ResponseEntity<>(responseDto, HttpStatus.NOT_FOUND);


    }
    public ResponseEntity<?> deleteById(long id){
        if(elevatorInfoRepository.existsById(id)){
            elevatorInfoRepository.deleteById(id);

            jpaSqlQueryService.saveJpaQuery(LocalDateTime.now(),"ElevatorInfoService.Class, deleteById Method","delete from elevator_info where id="+id);

            responseDto.setStatus(HttpStatus.ACCEPTED);
            responseDto.setDescription("elevator info deleted successfully");
            return new ResponseEntity<>(responseDto, HttpStatus.ACCEPTED);
        }
        responseDto.setStatus(HttpStatus.NOT_FOUND);
        responseDto.setDescription("elevator  info with provided id not found");

        return new ResponseEntity<>(responseDto, HttpStatus.NOT_FOUND);


    }

}

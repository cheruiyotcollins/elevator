package com.ezra.elevator.service;


import com.ezra.elevator.dto.AddElevatorRequest;
import com.ezra.elevator.dto.ResponseDto;
import com.ezra.elevator.model.Elevator;
import com.ezra.elevator.model.ElevatorInfo;
import com.ezra.elevator.model.JpaSqlQuery;
import com.ezra.elevator.repository.ElevatorInfoRepository;
import com.ezra.elevator.repository.ElevatorRepository;
import com.ezra.elevator.repository.JpaSqlQueryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class ElevatorService {

    private final ElevatorRepository elevatorRepository;


    private final ElevatorInfoRepository elevatorInfoRepository;
    @Autowired
    JpaSqlQueryService jpaSqlQueryService;


    ResponseDto responseDto = new ResponseDto();

    @Transactional
    public ResponseEntity<?> addElevator(AddElevatorRequest addElevatorRequest){

        try{

//            calling setUpElevator method to set up elevator using addElevatorRequest
            Elevator elevator=setUpElevator(addElevatorRequest);
            //saving elevator
            Elevator newElevator= elevatorRepository.save(elevator);
            log.info("setting up sql query performed by JPA On DB");
           jpaSqlQueryService.saveJpaQuery(LocalDateTime.now(),"ElevatorService.Class, Add Elevator Method",setAddElevatorQuery(elevator));

            log.info("initializing initial elevator information");
            ElevatorInfo elevatorInfo=new ElevatorInfo();
            elevatorInfo.setDirection("N/A");
            elevatorInfo.setState("STOPPED");
            elevatorInfo.setPostionFloorNo(0);
            elevatorInfo.setEventTime(LocalDateTime.now());
            elevatorInfo.setElevator(newElevator);
            elevatorInfoRepository.save(elevatorInfo);
            //adding sql query to db
            StringBuilder addElevatorInfoSql=new StringBuilder();
            addElevatorInfoSql.append("insert into elevator_info (postion_floor_no,direction,elevator_id,event_time,state) values ");
            addElevatorInfoSql.append("(");
            addElevatorInfoSql.append(0);
            addElevatorInfoSql.append(",");
            addElevatorInfoSql.append(elevatorInfo.getDirection());
            addElevatorInfoSql.append(",");
            addElevatorInfoSql.append(elevatorInfo.getElevator().getId());
            addElevatorInfoSql.append(",");
            addElevatorInfoSql.append(elevatorInfo.getEventTime());
            addElevatorInfoSql.append(",");
            addElevatorInfoSql.append(elevatorInfo.getState());
            addElevatorInfoSql.append(")");
            //setting up and calling JpaSqlQueryService

            jpaSqlQueryService.saveJpaQuery(LocalDateTime.now(),"ElevatorService.Class, Add Elevator Method",setAddElevatorQuery(elevator));


            responseDto.setPayload(elevator);
            responseDto.setStatus(HttpStatus.CREATED);
            responseDto.setDescription("Elevator added Successfully");

            return new ResponseEntity<>(responseDto, HttpStatus.CREATED);




        }catch(Exception e){
            responseDto.setStatus(HttpStatus.BAD_REQUEST);
            responseDto.setDescription("Something went wrong, elevator not added");

            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
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
            jpaSqlQueryService.saveJpaQuery(LocalDateTime.now(),"ElevatorService.Class, update Elevator Method",setAddElevatorQuery(elevator));

            responseDto.setStatus(HttpStatus.CREATED);
            responseDto.setDescription("Elevator updated Successfully");
            return new ResponseEntity<>(responseDto, HttpStatus.CREATED);




        }catch(Exception e){
            responseDto.setStatus(HttpStatus.BAD_REQUEST);
            responseDto.setDescription("Something went wrong, elevator not added");

            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
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
    public String setAddElevatorQuery(Elevator elevator){
        StringBuilder addElevatorSQL=new StringBuilder();
        addElevatorSQL.append("insert into elevators (manufacturer,max_load_kg,max_no_of_people,name) values ");
        addElevatorSQL.append("(");
        addElevatorSQL.append(elevator.getManufacturer());
        addElevatorSQL.append(",");
        addElevatorSQL.append(elevator.getMaxLoadKg());
        addElevatorSQL.append(",");
        addElevatorSQL.append(elevator.getMaxNoOfPeople());
        addElevatorSQL.append(",");
        addElevatorSQL.append(elevator.getName());
        addElevatorSQL.append(")");

        return addElevatorSQL.toString();

    }
    public ResponseEntity<?> findAll(){
      jpaSqlQueryService.saveJpaQuery(LocalDateTime.now(),"ElevatorService.Class, findAll Method","select * from elevator_info");
        return new ResponseEntity<>(elevatorRepository.findAll(), HttpStatus.FOUND);


    }
    public ResponseEntity<?> findById(long id){
        if(elevatorRepository.existsById(id)){
           jpaSqlQueryService.saveJpaQuery(LocalDateTime.now(),"ElevatorService.Class, findById Method","select * from elevator_info where id="+id);

            return new ResponseEntity<>(elevatorRepository.findById(id).get(), HttpStatus.FOUND);
        }
        responseDto.setStatus(HttpStatus.NOT_FOUND);
        responseDto.setDescription("elevator with provided id not found");

        return new ResponseEntity<>(responseDto, HttpStatus.NOT_FOUND);


    }
    public ResponseEntity<?> deleteById(long id){
        if(elevatorRepository.existsById(id)){
            jpaSqlQueryService.saveJpaQuery(LocalDateTime.now(),"ElevatorService.Class, deleteById Method","delete from elevators where id="+id);
            elevatorRepository.deleteById(id);
            responseDto.setStatus(HttpStatus.OK);
            responseDto.setDescription("elevator deleted successfully");
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }
        responseDto.setStatus(HttpStatus.NOT_FOUND);
        responseDto.setDescription("elevator with provided id not found");

        return new ResponseEntity<>(responseDto, HttpStatus.NOT_FOUND);


    }

}

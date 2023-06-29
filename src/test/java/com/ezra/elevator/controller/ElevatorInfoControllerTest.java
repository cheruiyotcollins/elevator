package com.ezra.elevator.controller;


import com.ezra.elevator.dto.GeneralResponse;
import com.ezra.elevator.dto.UpdateElevatorInfoRequest;
import com.ezra.elevator.model.Elevator;
import com.ezra.elevator.model.ElevatorInfo;
import com.ezra.elevator.repository.ElevatorInfoRepository;
import com.ezra.elevator.repository.ElevatorRepository;
import com.ezra.elevator.repository.EventLogRepository;
import com.ezra.elevator.service.ElevatorInfoService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringJUnitConfig
@SpringBootTest
public class ElevatorInfoControllerTest {
    @MockBean
     ElevatorInfoRepository elevatorInfoRepository;
    @MockBean
     ElevatorRepository elevatorRepository;
    @MockBean
    EventLogRepository eventLogRepository;
    @Test
    public void testAddElevator(){
        when(elevatorRepository.save(Mockito.any())).thenReturn(new Elevator());
        when(elevatorInfoRepository.save(Mockito.any())).thenReturn(new ElevatorInfo());
        ElevatorInfoController elevatorController=new ElevatorInfoController(new ElevatorInfoService(elevatorInfoRepository,elevatorRepository,eventLogRepository));
        ResponseEntity<?> actualAddElevatorInfoResult= elevatorController.addOrUpdateElevatorInfo(new UpdateElevatorInfoRequest(1,1,1L));
        assertTrue(actualAddElevatorInfoResult.hasBody());
        assertTrue(actualAddElevatorInfoResult.getHeaders().isEmpty());
        assertEquals(404, actualAddElevatorInfoResult.getStatusCode().value());
        GeneralResponse body = (GeneralResponse) actualAddElevatorInfoResult.getBody();
        assertEquals(HttpStatus.NOT_FOUND, body.getStatus());
        Object payloadResult = body.getPayload();

    }
    @Test
    void testFindById() {

        ElevatorInfoController elevatorInfoController=new ElevatorInfoController(new ElevatorInfoService(elevatorInfoRepository,elevatorRepository,eventLogRepository));

        ResponseEntity<?> actualAddElevatorInfoResult= elevatorInfoController.findById(0L);
        assertTrue(actualAddElevatorInfoResult.hasBody());
        assertTrue(actualAddElevatorInfoResult.getHeaders().isEmpty());
        assertEquals(404, actualAddElevatorInfoResult.getStatusCode().value());


    }
    @Test
    void testViewAllElevators() {
        ArrayList<ElevatorInfo> elevatorInfoArrayList = new ArrayList<>();
        ElevatorInfo elevatorInfo = new ElevatorInfo();
        elevatorInfoArrayList.add(elevatorInfo);
        when(elevatorInfoRepository.findAll()).thenReturn(elevatorInfoArrayList);
        ResponseEntity<?> actualViewAllResult = (new ElevatorInfoController(new ElevatorInfoService( elevatorInfoRepository,elevatorRepository,eventLogRepository))).findAll();
        assertTrue(actualViewAllResult.hasBody());
        assertTrue(actualViewAllResult.getHeaders().isEmpty());
        assertEquals(302, actualViewAllResult.getStatusCode().value());

    }
}
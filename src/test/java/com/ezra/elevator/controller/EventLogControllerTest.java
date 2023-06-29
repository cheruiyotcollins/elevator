package com.ezra.elevator.controller;


import com.ezra.elevator.model.Elevator;
import com.ezra.elevator.model.ElevatorInfo;
import com.ezra.elevator.model.EventLog;
import com.ezra.elevator.repository.ElevatorInfoRepository;
import com.ezra.elevator.repository.ElevatorRepository;
import com.ezra.elevator.repository.EventLogRepository;
import com.ezra.elevator.service.EventLogService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringJUnitConfig
@SpringBootTest
public class EventLogControllerTest {
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
        when(eventLogRepository.save(Mockito.any())).thenReturn(new EventLog());

       }
    @Test
    void testFindById() {

        EventLogController eventLogController=new EventLogController(new EventLogService(elevatorInfoRepository,elevatorRepository,eventLogRepository));

        ResponseEntity<?> actualAddElevatorEventLogResult= eventLogController.findByLogId(0L);
        assertTrue(actualAddElevatorEventLogResult.hasBody());
        assertTrue(actualAddElevatorEventLogResult.getHeaders().isEmpty());
        assertEquals(404, actualAddElevatorEventLogResult.getStatusCode().value());


    }
    @Test
    void testViewAllElevators() {
        ArrayList<EventLog> eventLogArrayList = new ArrayList<>();
        EventLog eventLog = new EventLog();
        eventLogArrayList.add(eventLog);
        when(eventLogRepository.findAll()).thenReturn(eventLogArrayList);
        ResponseEntity<?> actualViewAllResult = (new EventLogController(new EventLogService( elevatorInfoRepository,elevatorRepository,eventLogRepository))).findAll();
        assertTrue(actualViewAllResult.hasBody());
        assertTrue(actualViewAllResult.getHeaders().isEmpty());
        assertEquals(302, actualViewAllResult.getStatusCode().value());

    }
}
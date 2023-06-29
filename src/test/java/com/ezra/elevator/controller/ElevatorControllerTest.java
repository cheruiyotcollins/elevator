package com.ezra.elevator.controller;

import com.ezra.elevator.dto.AddElevatorRequest;
import com.ezra.elevator.dto.GeneralResponse;
import com.ezra.elevator.model.Elevator;
import com.ezra.elevator.repository.ElevatorInfoRepository;
import com.ezra.elevator.repository.ElevatorRepository;
import com.ezra.elevator.service.ElevatorService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;


@SpringJUnitConfig

@SpringBootTest
public class ElevatorControllerTest {
    @MockBean
    ElevatorRepository elevatorRepository;
    @MockBean
    ElevatorInfoRepository elevatorInfoRepository;
    @Test
    public void testAddElevator(){
        when(elevatorRepository.save(Mockito.any())).thenReturn(new Elevator());
        ElevatorController elevatorController=new ElevatorController(new ElevatorService(elevatorRepository,elevatorInfoRepository));
        ResponseEntity<?> actualAddElevatorResult= elevatorController.addElevator(new AddElevatorRequest("Elevator Name","Manufacturer Name",1,1));
        assertTrue(actualAddElevatorResult.hasBody());
        assertTrue(actualAddElevatorResult.getHeaders().isEmpty());
        assertEquals(201, actualAddElevatorResult.getStatusCode().value());
        GeneralResponse body = (GeneralResponse) actualAddElevatorResult.getBody();
         assertEquals(HttpStatus.CREATED, body.getStatus());
        Object payloadResult = body.getPayload();
        assertTrue(payloadResult instanceof Elevator);
        assertNotNull(((Elevator) payloadResult).getId());
        assertNotNull(((Elevator) payloadResult).getMaxLoadKg());
        assertNotNull(((Elevator) payloadResult).getName());
        assertNotNull(((Elevator) payloadResult).getManufacturer());
        assertNotNull(((Elevator) payloadResult).getMaxNoOfPeople());
//        verify(elevatorRepository).save(Mockito.any());
//        verify(elevatorRepository).findById(Mockito.<Long>any());


    }
    @Test
    void testFindById() {

        ElevatorController elevatorController=new ElevatorController(new ElevatorService(elevatorRepository,elevatorInfoRepository));

        ResponseEntity<?> actualAddElevatorResult= elevatorController.findById(0L);
        assertTrue(actualAddElevatorResult.hasBody());
        assertTrue(actualAddElevatorResult.getHeaders().isEmpty());
        assertEquals(404, actualAddElevatorResult.getStatusCode().value());


    }
    @Test
    void testViewAllElevators() {
        ArrayList<Elevator> elevators = new ArrayList<>();
        Elevator card = new Elevator();
        elevators.add(card);
        when(elevatorRepository.findAll()).thenReturn(elevators);
        ResponseEntity<?> actualViewAllResult = (new ElevatorController(new ElevatorService(elevatorRepository, elevatorInfoRepository))).findAll();
        assertTrue(actualViewAllResult.hasBody());
        assertTrue(actualViewAllResult.getHeaders().isEmpty());
        assertEquals(302, actualViewAllResult.getStatusCode().value());
        verify(elevatorRepository).findAll();
    }


}

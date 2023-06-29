package com.ezra.elevator.service;


import com.ezra.elevator.dto.AddElevatorRequest;
import com.ezra.elevator.dto.GeneralResponse;
import com.ezra.elevator.model.Elevator;
import com.ezra.elevator.repository.ElevatorInfoRepository;
import com.ezra.elevator.repository.ElevatorRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;

@SpringJUnitConfig
@SpringBootTest
public class ElevatorServiceTest {
    @MockBean
    ElevatorRepository elevatorRepository;

    @MockBean
    ElevatorInfoRepository elevatorInfoRepository;

    @Test
    public void testCreateElevatorReturnsElevatorExists() {
        Elevator elevator = new Elevator();
        when(elevatorRepository.findById(Mockito.any())).thenReturn(Optional.of(elevator));
        ElevatorService elevatorService = new ElevatorService(elevatorRepository, elevatorInfoRepository);
        ResponseEntity<?> actualCreateElevatorResult = elevatorService.addElevator(
                new AddElevatorRequest("Elevator Name","Manufacturer Name",1,1));
        assertTrue(actualCreateElevatorResult.hasBody());
        GeneralResponse generalResponse= (GeneralResponse) actualCreateElevatorResult.getBody();
        Object payloadResult = generalResponse.getPayload();
        assertEquals("Elevator added Successfully", generalResponse.getDescription());
        assertEquals(HttpStatus.CREATED, generalResponse.getStatus());
        assertNotNull(((Elevator) payloadResult).getId());
        assertNotNull(((Elevator) payloadResult).getMaxLoadKg());
        assertNotNull(((Elevator) payloadResult).getName());
        assertNotNull(((Elevator) payloadResult).getManufacturer());
        assertNotNull(((Elevator) payloadResult).getMaxNoOfPeople());

    }


    @Test
    public void testViewElevatorFailsToFindAElevator() throws Exception {
        Elevator elevator = new Elevator();
        when(elevatorRepository.findById(Mockito.any())).thenReturn(Optional.of(elevator));
        ElevatorService elevatorService = new ElevatorService(elevatorRepository, elevatorInfoRepository);
        ResponseEntity<?> actualCreateElevatorResult = elevatorService.addElevator(
                new AddElevatorRequest("Elevator Name","Manufacturer Name",1,1));
        GeneralResponse generalResponse= (GeneralResponse) actualCreateElevatorResult.getBody();
        assertEquals("Elevator added Successfully", generalResponse.getDescription());
        assertEquals(HttpStatus.CREATED, generalResponse.getStatus());

        ResponseEntity<?> failResponse=elevatorService.findById(100L);
        GeneralResponse generalResponse1= (GeneralResponse) failResponse.getBody();
        assertEquals("elevator with provided id not found", generalResponse.getDescription());
        assertEquals(HttpStatus.NOT_FOUND, generalResponse.getStatus());
    }

    @Test
    void testViewAllElevators() {
        ArrayList<Elevator> content = new ArrayList<>();
        content.add(new Elevator());
        PageImpl<Elevator> pageImpl = new PageImpl<>(content);
        ElevatorRepository repository = mock(ElevatorRepository.class);
        when(elevatorRepository.findAll(Mockito.<Pageable>any())).thenReturn(pageImpl);
        ResponseEntity<?> actualViewAllCardsResult = (new ElevatorService(elevatorRepository, mock(ElevatorInfoRepository.class))).findAll();
        List<Object> generalResponse= (List<Object>) actualViewAllCardsResult.getBody();


         assertEquals(HttpStatus.FOUND, actualViewAllCardsResult.getStatusCode());



    }






}

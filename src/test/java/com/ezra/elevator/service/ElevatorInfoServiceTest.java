package com.ezra.elevator.service;

import com.ezra.elevator.dto.AddElevatorRequest;
import com.ezra.elevator.dto.ResponseDto;
import com.ezra.elevator.dto.UpdateElevatorInfoRequest;
import com.ezra.elevator.model.Elevator;
import com.ezra.elevator.model.ElevatorInfo;
import com.ezra.elevator.repository.ElevatorInfoRepository;
import com.ezra.elevator.repository.ElevatorRepository;
import com.ezra.elevator.repository.JpaSqlQueryRepository;
import com.ezra.elevator.repository.EventLogRepository;
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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringJUnitConfig
@SpringBootTest
public class ElevatorInfoServiceTest {
    @MockBean
    ElevatorInfoRepository elevatorInfoRepository;
    @MockBean
    ElevatorRepository elevatorRepository;
    @MockBean
    EventLogRepository eventLogRepository;
    @MockBean
    JpaSqlQueryRepository jpaSqlQueryRepository;

    @Test
    public void testCreateElevatorInfoReturnsElevatorInfoExists() {
        Elevator elevator = new Elevator();
        when(elevatorRepository.findById(Mockito.any())).thenReturn(Optional.of(elevator));
        ElevatorService elevatorService = new ElevatorService(elevatorRepository, elevatorInfoRepository, jpaSqlQueryRepository);
        ResponseEntity<?> actualCreateElevatorResult = elevatorService.addElevator(
                new AddElevatorRequest("Elevator Name","Manufacturer Name",1,1));
        assertTrue(actualCreateElevatorResult.hasBody());
        ResponseDto generalResponse= (ResponseDto) actualCreateElevatorResult.getBody();
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
    public void testViewElevatorInfoFailsToFindAElevatorInfo() throws Exception {
        ElevatorInfo elevator = new ElevatorInfo();
        when(elevatorInfoRepository.findById(Mockito.any())).thenReturn(Optional.of(elevator));
        ElevatorInfoService elevatorInfoService = new ElevatorInfoService(elevatorInfoRepository,elevatorRepository,eventLogRepository);
        ResponseEntity<?> actualCreateElevatorResult = elevatorInfoService.createOrUpdateElevatorInfo(
                new UpdateElevatorInfoRequest(1,1,1L));
        ResponseDto generalResponse= (ResponseDto) actualCreateElevatorResult.getBody();

        assertEquals(HttpStatus.NOT_FOUND, generalResponse.getStatus());

        ResponseEntity<?> failResponse=elevatorInfoService.findById(100L);
        ResponseDto generalResponse1= (ResponseDto) failResponse.getBody();
        assertEquals("elevator info with provided id not found", generalResponse.getDescription());
        assertEquals(HttpStatus.NOT_FOUND, generalResponse.getStatus());
    }

    @Test
    void testViewAllElevatorInfo() {
        ArrayList<ElevatorInfo> content = new ArrayList<>();
        content.add(new ElevatorInfo());
        PageImpl<ElevatorInfo> pageImpl = new PageImpl<>(content);
        ElevatorInfoRepository repository = mock(ElevatorInfoRepository.class);
        when(elevatorInfoRepository.findAll(Mockito.<Pageable>any())).thenReturn(pageImpl);
        ResponseEntity<?> actualViewAllCardsResult = (new ElevatorService(elevatorRepository, mock(ElevatorInfoRepository.class), jpaSqlQueryRepository)).findAll();
        List<Object> generalResponse= (List<Object>) actualViewAllCardsResult.getBody();


        assertEquals(HttpStatus.FOUND, actualViewAllCardsResult.getStatusCode());



    }


}

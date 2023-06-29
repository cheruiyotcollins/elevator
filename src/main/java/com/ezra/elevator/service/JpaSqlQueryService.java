package com.ezra.elevator.service;

import com.ezra.elevator.dto.ResponseDto;
import com.ezra.elevator.model.JpaSqlQuery;
import com.ezra.elevator.repository.JpaSqlQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class JpaSqlQueryService {
    @Autowired
    JpaSqlQueryRepository jpaSqlQueryRepository;
    ResponseDto responseDto=new ResponseDto();


    public ResponseEntity<ResponseDto> findAllSQLQueries(){

        try{
           responseDto.setPayload(jpaSqlQueryRepository.findAll());
           responseDto.setStatus(HttpStatus.FOUND);
           responseDto.setDescription("Success");

        }catch (Exception e){
            responseDto.setStatus(HttpStatus.NOT_FOUND);
            responseDto.setDescription("Fail");
        }
        return  new ResponseEntity<>(responseDto,responseDto.getStatus());
    }

    public ResponseEntity<ResponseDto> findSQLQueriesById(long id){
        if(jpaSqlQueryRepository.existsById(id)){
            responseDto.setPayload(jpaSqlQueryRepository.findAll());
            responseDto.setStatus(HttpStatus.FOUND);
            responseDto.setDescription("Success");
            return  new ResponseEntity<>(responseDto,responseDto.getStatus());
        }
            responseDto.setStatus(HttpStatus.NOT_FOUND);
            responseDto.setDescription("Fail");

        return  new ResponseEntity<>(responseDto,responseDto.getStatus());
    }


}

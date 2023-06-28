package com.ezra.elevator.service;

import com.ezra.elevator.repository.ElevatorSqlQueryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ElevatorSqlQueryService {
    @Autowired
    ElevatorSqlQueryRepository elevatorSqlQueryRepository;
}

package com.ezra.elevator.service;

import com.ezra.elevator.repository.ElevatorSqlQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ElevatorSqlQueryService {
    @Autowired
    ElevatorSqlQueryRepository elevatorSqlQueryRepository;
}

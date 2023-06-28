package com.ezra.elevator.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/elevators/")
@Slf4j
@RequiredArgsConstructor
public class ElevatorSqlQueryController {
}

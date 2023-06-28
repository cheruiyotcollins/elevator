package com.ezra.elevator.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateElevatorInfoRequest {

     private int callerPosition;

     private int destination;

    private long elevatorId;

}

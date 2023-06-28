package com.ezra.elevator.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddElevatorRequest {
    @NotEmpty
    private String name;
    @NotEmpty
    private String manufacturer;

    @NotEmpty
    private int maxNoOfPeople;
    @NotEmpty
    private int maxLoadKg;
}

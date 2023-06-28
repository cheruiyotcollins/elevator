package com.ezra.elevator.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="elevators")
@Entity
public class Elevator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    @NotEmpty
    private String name;
    @NotEmpty
    private String manufacturer;


    private int maxNoOfPeople;

    private int maxLoadKg;




}

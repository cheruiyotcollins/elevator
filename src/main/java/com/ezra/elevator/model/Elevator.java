package com.ezra.elevator.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

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
    private int place;

    @NotEmpty
    private String state;
    @NotEmpty
    private String direction;
    @NotEmpty
    private Timestamp eventTime;

}

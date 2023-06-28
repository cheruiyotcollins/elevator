package com.ezra.elevator.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="elevator_info")
@Entity
public class ElevatorInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;



    private int place;

    @NotEmpty
    private String state;
    @NotEmpty
    private String direction;

    private LocalDateTime eventTime;
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "elevatorId", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Elevator elevator;

}

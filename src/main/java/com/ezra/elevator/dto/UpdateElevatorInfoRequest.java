package com.ezra.elevator.dto;

import com.ezra.elevator.model.Elevator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateElevatorInfoRequest {
    @NotEmpty
    private int place;

    @NotEmpty
    private String state;
    @NotEmpty
    private String direction;

    private long elevator_id;

}

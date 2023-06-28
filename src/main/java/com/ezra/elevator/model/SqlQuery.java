package com.ezra.elevator.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="elevators_sql_queries")
@Entity
public class SqlQuery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

     private int calledFrom;
     private String whoCalled;

     private String sqlQuery;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "elevator_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Elevator elevator;


}

package com.ezra.elevator.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity

@Table(name="jpa_sql_queries")
public class JpaSqlQuery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

     private String calledFrom;
     private LocalDateTime localDateTime;

     private String sqlQuery;



}

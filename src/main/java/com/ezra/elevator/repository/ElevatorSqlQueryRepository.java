package com.ezra.elevator.repository;

import com.ezra.elevator.model.ElevatorSqlQuery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ElevatorSqlQueryRepository extends JpaRepository<ElevatorSqlQuery,Long> {
}

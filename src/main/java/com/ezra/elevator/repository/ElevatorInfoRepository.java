package com.ezra.elevator.repository;

import com.ezra.elevator.model.Elevator;
import com.ezra.elevator.model.ElevatorInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ElevatorInfoRepository extends JpaRepository<ElevatorInfo,Long> {
    @Query("SELECT e FROM ElevatorInfo e where e.elevator = :elevator")
    Optional<ElevatorInfo> findByElevator(Elevator elevator);
}

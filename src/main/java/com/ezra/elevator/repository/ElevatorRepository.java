package com.ezra.elevator.repository;

import com.ezra.elevator.model.Elevator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ElevatorRepository extends JpaRepository<Elevator, Long> {
}

package com.ezra.elevator.repository;

import com.ezra.elevator.model.Elevator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ElevatorRepository extends JpaRepository<Elevator, Long> {
//    @Query(
//            "SELECT CASE WHEN COUNT(s) > 0 THEN TRUE ELSE FALSE END FROM Person s WHERE s.personId = ?1")
//    Boolean  isPersonExitsById(Integer id);
}

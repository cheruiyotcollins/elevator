package com.ezra.elevator.repository;

import com.ezra.elevator.model.ElevatorInfo;
import com.ezra.elevator.model.EventLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EventLogRepository  extends JpaRepository<EventLog, Long> {
    @Query("SELECT e FROM EventLog e where e.elevatorInfo = :elevatorInfo")
    List<EventLog> findByElevatorInfo(ElevatorInfo elevatorInfo);
}

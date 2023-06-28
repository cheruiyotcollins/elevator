package com.ezra.elevator.repository;

import com.ezra.elevator.model.EventLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventLogRepository  extends JpaRepository<EventLog, Long> {
}

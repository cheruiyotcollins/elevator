package com.ezra.elevator.repository;

import com.ezra.elevator.model.JpaSqlQuery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaSqlQueryRepository extends JpaRepository<JpaSqlQuery,Long> {
}

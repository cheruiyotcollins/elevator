package com.ezra.elevator.repository;

import com.ezra.elevator.model.SqlQuery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SqlQueryRepository extends JpaRepository<SqlQuery,Long> {
}

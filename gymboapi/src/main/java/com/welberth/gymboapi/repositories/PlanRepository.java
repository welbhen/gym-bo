package com.welberth.gymboapi.repositories;

import com.welberth.gymboapi.models.Plan;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Data Access layer for the Plan model.
 */
@Repository
public interface PlanRepository extends JpaRepository<Plan, Long>{
}

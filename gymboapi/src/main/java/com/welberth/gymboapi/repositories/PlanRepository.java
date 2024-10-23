package com.welberth.gymboapi.repositories;

import com.welberth.gymboapi.models.Plan;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Data Access layer for the Plan model.
 */
@Repository
public interface PlanRepository extends JpaRepository<Plan, Long>{

    Optional<Plan> findByTitle(String title);
}

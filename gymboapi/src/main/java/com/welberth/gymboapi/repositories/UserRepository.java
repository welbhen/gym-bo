package com.welberth.gymboapi.repositories;

import com.welberth.gymboapi.models.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Data Access layer for the User model.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // All main methods we need are already available by inheritance of JpaRepository

    List<User> findByPlan_Id(Long id);

    Optional<User> findByUsername(String username);
}

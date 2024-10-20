package com.welberth.gymboapi.repositories;

import com.welberth.gymboapi.models.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // All methods we need are already available by inheritance of JpaRepository
}

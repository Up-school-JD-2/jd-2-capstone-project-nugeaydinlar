package io.upschool.repository;

import io.upschool.entity.Airline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AirlineRepository extends JpaRepository<Airline, Long> {
    Optional<Airline> findByNameIgnoreCase(String name);
    Optional<Airline> findById(Long id);

}
package io.upschool.repository;

import io.upschool.entity.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {
    Optional<Route> findByNameIgnoreCase(String name);
    Optional<Route>findById(Long id);
}

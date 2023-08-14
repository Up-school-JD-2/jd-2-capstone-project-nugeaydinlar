package io.upschool.controller;

import io.upschool.dto.airlineDTO.AirlineRequestDto;
import io.upschool.dto.airlineDTO.AirlineResponseDto;
import io.upschool.dto.flightDTO.FlightRequestDto;
import io.upschool.dto.flightDTO.FlightResponseDto;
import io.upschool.service.AirlineService;
import io.upschool.service.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/airlines")
@RequiredArgsConstructor
public class AirlineController {

    private final AirlineService airlineService;
    private final FlightService flightService;

    @GetMapping
    public ResponseEntity<List<AirlineResponseDto>> getAirlines() {
        List<AirlineResponseDto> airlines = airlineService.getAllAirlines();
        return ResponseEntity.ok(airlines);
    }

    @PostMapping("/create")
    public ResponseEntity<AirlineResponseDto> createAirline(@RequestBody AirlineRequestDto airlineRequestDto) {
        AirlineResponseDto response = airlineService.add(airlineRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<List<AirlineResponseDto>> searchAirlines(@PathVariable("name") String name) {
        List<AirlineResponseDto> airlines = airlineService.searchAirlinesByName(name);

        return ResponseEntity.ok(airlines);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AirlineResponseDto> updateAirline(
            @PathVariable("id") Long id, @RequestBody AirlineRequestDto airlineRequestDto) {
        AirlineResponseDto response = airlineService.updateAirline(id, airlineRequestDto);

        if (response == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAirline(@PathVariable("id") Long id) {
        airlineService.deleteAirline(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/flights/add")
    public ResponseEntity<FlightResponseDto> addFlightToAirline(@RequestBody FlightRequestDto flightRequestDto) {
        FlightResponseDto response = airlineService.addFlightToAirline(flightRequestDto);
        if (response == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/flights/{id}")
    public ResponseEntity<List<FlightResponseDto>> getFlightsByAirline(@PathVariable("id") Long id) {
        List<FlightResponseDto> flights = airlineService.getFlightsByAirline(id);
        return ResponseEntity.ok(flights);
    }

    @DeleteMapping("/flights/{id}")
    public ResponseEntity<Void> deleteFlight(@PathVariable("id") Long id) {
        flightService.deleteFlight(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/flights/update/{id}")
    public ResponseEntity<FlightResponseDto> updateFlight(
            @PathVariable("id") Long id, @RequestBody FlightRequestDto flightRequestDto) {
        FlightResponseDto response = flightService.updateFlight(id, flightRequestDto);

        if (response == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(response);
    }
}
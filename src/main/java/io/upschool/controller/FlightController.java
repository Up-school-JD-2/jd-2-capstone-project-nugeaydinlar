package io.upschool.controller;

import io.upschool.dto.flightDTO.FlightRequestDto;
import io.upschool.dto.flightDTO.FlightResponseDto;
import io.upschool.service.AirlineService;
import io.upschool.service.FlightService;
import io.upschool.service.RouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/flights")
@RequiredArgsConstructor
public class FlightController {

    private final FlightService flightService;
    private final AirlineService airlineService;
    private final RouteService routeService;

    @GetMapping
    public ResponseEntity<List<FlightResponseDto>> getAllFlights() {
        List<FlightResponseDto> flights = flightService.getAllFlights();
        return ResponseEntity.ok(flights);
    }

    @DeleteMapping("/{flightId}")
    public ResponseEntity<Void> deleteFlight(@PathVariable("flightId") Long flightId) {
        flightService.deleteFlight(flightId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update/{flightId}")
    public ResponseEntity<FlightResponseDto> updateFlight(
            @PathVariable("flightId") Long flightId, @RequestBody FlightRequestDto flightRequestDto) {
        FlightResponseDto response = flightService.updateFlight(flightId, flightRequestDto);

        if (response == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(response);
    }
}
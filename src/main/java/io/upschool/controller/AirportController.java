package io.upschool.controller;

import io.upschool.dto.airportDTO.AirportRequestDto;
import io.upschool.dto.airportDTO.AirportResponseDto;
import io.upschool.service.AirportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/airports")
@RequiredArgsConstructor
public class AirportController {

    private final AirportService airportService;

    @GetMapping
    public ResponseEntity<List<AirportResponseDto>> getAirports() {
        List<AirportResponseDto> airports = airportService.getAllAirports();
        return ResponseEntity.ok(airports);
    }

    @PostMapping("/create")
    public ResponseEntity<AirportResponseDto> createAirport(@RequestBody AirportRequestDto airportRequestDto) {
        AirportResponseDto response = airportService.add(airportRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @GetMapping("/search/{name}")
    public ResponseEntity<List<AirportResponseDto>> searchAirports(@PathVariable("name") String name) {
        List<AirportResponseDto> airports = airportService.searchAirportsByName(name);

        return ResponseEntity.ok(airports);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<AirportResponseDto> updateAirport(@PathVariable("id") Long id, @RequestBody AirportRequestDto airportRequestDto) {
        AirportResponseDto response = airportService.updateAirport(id, airportRequestDto);

        if (response == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAirport(@PathVariable("id") Long id) {
        airportService.deleteAirport(id);
        return ResponseEntity.noContent().build();
    }
}
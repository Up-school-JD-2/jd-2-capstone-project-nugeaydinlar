package io.upschool.controller;

import io.upschool.dto.routeDTO.RouteRequestDto;
import io.upschool.dto.routeDTO.RouteResponseDto;
import io.upschool.service.RouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/routes")
@RequiredArgsConstructor
public class RouteController {

    private final RouteService routeService;

    @GetMapping
    public ResponseEntity<List<RouteResponseDto>> getRoutes() {
        List<RouteResponseDto> routes = routeService.getAllRoutes();
        return ResponseEntity.ok(routes);
    }
    @PostMapping("/create")
    public ResponseEntity<RouteResponseDto> createRoute(@RequestBody RouteRequestDto routeRequestDto) {
        RouteResponseDto response = routeService.addRoute(routeRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<List<RouteResponseDto>> searchRoutes(@PathVariable("name") String name) {
        List<RouteResponseDto> routes = routeService.searchRoutesByName(name);

        return ResponseEntity.ok(routes);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<RouteResponseDto> updateRoute(@PathVariable("id") Long id, @RequestBody RouteRequestDto routeRequestDto) {
        RouteResponseDto response = routeService.updateRoute(id, routeRequestDto);

        if (response == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Void> deleteRoute(@PathVariable("id") Long id) {
        routeService.deleteRoute(id);
        return ResponseEntity.noContent().build();
    }
}


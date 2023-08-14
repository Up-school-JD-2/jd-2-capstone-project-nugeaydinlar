package io.upschool.service;

import io.upschool.dto.routeDTO.RouteRequestDto;
import io.upschool.dto.routeDTO.RouteResponseDto;
import io.upschool.entity.Airline;
import io.upschool.entity.Airport;
import io.upschool.entity.Route;
import io.upschool.exception.BadRequestExcepiton;
import io.upschool.exception.NotFoundException;
import io.upschool.repository.AirlineRepository;
import io.upschool.repository.AirportRepository;
import io.upschool.repository.RouteRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RouteService {

    private final RouteRepository routeRepository;
    private final AirportRepository airportRepository;
    private final AirlineRepository airlineRepository;

    @Transactional
    public RouteResponseDto addRoute(RouteRequestDto routeRequestDto) {
        try {
            Airport sourceAirport = airportRepository.findById(routeRequestDto.sourceAirportId)
                    .orElseThrow(() -> new NotFoundException("Kaynak havalimanı bulunamadı."));
            Airport destinationAirport = airportRepository.findById(routeRequestDto.destinationAirportId)
                    .orElseThrow(() -> new NotFoundException("Varış havalimanı bulunamadı."));
            Airline airline = airlineRepository.findById(routeRequestDto.airlineId)
                    .orElseThrow(() -> new NotFoundException("Havayolu bulunamadı."));

            Route newRoute = Route.builder()
                    .distance(routeRequestDto.getDistance())
                    .name(routeRequestDto.getName())
                    .sourceAirport(sourceAirport)
                    .destinationAirport(destinationAirport)
                    .airline(airline)
                    .build();

            newRoute = routeRepository.save(newRoute);

            return RouteResponseDto.builder()
                    .id(newRoute.getId())
                    .distance(newRoute.getDistance())
                    .name(newRoute.getName())
                    .sourceAirportId(routeRequestDto.getSourceAirportId())
                    .destinationAirportId(routeRequestDto.getDestinationAirportId())
                    .airlineId(routeRequestDto.getAirlineId())
                    .build();
        } catch (NotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new BadRequestExcepiton("Rota eklenirken bir hata oluştu.");
        }
    }


    public List<RouteResponseDto> getAllRoutes() {
        try {
            List<Route> routes = routeRepository.findAll();
            return routes.stream().map(route -> RouteResponseDto.builder()
                            .id(route.getId())
                            .distance(route.getDistance())
                            .name(route.getName())
                            .sourceAirportId(Long.valueOf(String.valueOf(route.getSourceAirport().getId())))
                            .destinationAirportId(Long.valueOf(String.valueOf(route.getDestinationAirport().getId())))
                            .airlineId(Long.valueOf(String.valueOf(route.getAirline().getId())))
                            .build())
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            throw new BadRequestExcepiton("Rotalar alınırken bir hata oluştu.");
        }
    }

   @Transactional
    public RouteResponseDto updateRoute(Long routeId, RouteRequestDto routeRequestDto) {
        try {
            Route existingRoute = routeRepository.findById(routeId)
                    .orElseThrow(() -> new NotFoundException("Rota bulunamadı."));

            Airport sourceAirport = airportRepository.findById(routeRequestDto.sourceAirportId)
                    .orElseThrow(() -> new NotFoundException("Kaynak havalimanı bulunamadı."));
            Airport destinationAirport = airportRepository.findById(routeRequestDto.destinationAirportId)
                    .orElseThrow(() -> new NotFoundException("Varış havalimanı bulunamadı."));
            Airline airline = airlineRepository.findById(routeRequestDto.airlineId)
                    .orElseThrow(() -> new NotFoundException("Havayolu bulunamadı."));

            existingRoute.setName(routeRequestDto.getName());
            existingRoute.setDistance(routeRequestDto.getDistance());
            existingRoute.setSourceAirport(sourceAirport);
            existingRoute.setDestinationAirport(destinationAirport);
            existingRoute.setAirline(airline);

            existingRoute = routeRepository.save(existingRoute);

            return RouteResponseDto.builder()
                    .id(existingRoute.getId())
                    .name(existingRoute.getName())
                    .distance(existingRoute.getDistance())
                    .sourceAirportId(routeRequestDto.getSourceAirportId())
                    .destinationAirportId(routeRequestDto.getDestinationAirportId())
                    .airlineId(routeRequestDto.getAirlineId())
                    .build();
        } catch (NotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new BadRequestExcepiton("Rota güncellenirken bir hata oluştu.");
        }
    }

    public List<RouteResponseDto> searchRoutesByName(String name) {
        List<RouteResponseDto> routes = getAllRoutes()
                .stream()
                .filter(route -> route.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());

        if (routes.isEmpty()) {
            throw new NotFoundException("İlgili isimde rota bulunamadı.");
        }

        return routes;
    }
    public void deleteRoute(Long routeId) {
        try {
            routeRepository.deleteById(routeId);
        } catch (Exception ex) {
            throw new BadRequestExcepiton("Rota silinirken bir hata oluştu.");
        }
    }


    public Route getRouteById(Long routeId) {
        try {
            return routeRepository.findById(routeId).orElse(null);
        } catch (Exception ex) {
            throw new BadRequestExcepiton("Rota bilgisi alınırken bir hata oluştu.");
        }
    }
}
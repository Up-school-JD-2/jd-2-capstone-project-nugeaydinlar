package io.upschool.service;

import io.upschool.dto.airlineDTO.AirlineRequestDto;
import io.upschool.dto.airlineDTO.AirlineResponseDto;
import io.upschool.dto.flightDTO.FlightRequestDto;
import io.upschool.dto.flightDTO.FlightResponseDto;
import io.upschool.entity.Airline;
import io.upschool.entity.Flight;
import io.upschool.entity.Route;
import io.upschool.exception.BadRequestExcepiton;
import io.upschool.exception.NotFoundException;
import io.upschool.repository.AirlineRepository;
import io.upschool.repository.FlightRepository;
import io.upschool.repository.RouteRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AirlineService {

    private final AirlineRepository airlineRepository;
    private final FlightRepository flightRepository;
    private final RouteRepository routeRepository;

    @Transactional
    public AirlineResponseDto add(AirlineRequestDto airlineRequestDto) {
        try {
            Airline newAirline = Airline.builder()
                    .name(airlineRequestDto.getName())
                    .country(airlineRequestDto.getCountry())
                    .build();
            Airline addedAirline = airlineRepository.save(newAirline);
            return AirlineResponseDto.builder()
                    .id(addedAirline.getId())
                    .name(addedAirline.getName())
                    .country(addedAirline.getCountry())
                    .build();
        } catch (Exception ex) {
            throw new BadRequestExcepiton("Havayolları alınırken bir hata oluştu.");
        }
    }


    public List<AirlineResponseDto> getAllAirlines() {
        List<AirlineResponseDto> airlineResponseDtos = new ArrayList<>();

        try {
            List<Airline> airlines = airlineRepository.findAll();

            airlineResponseDtos = airlines.stream().map(airline -> AirlineResponseDto.builder()
                            .id(airline.getId())
                            .name(airline.getName())
                            .country(airline.getCountry())
                            .build())
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            // Hata durumunda sadece boş liste döndürülüyor
        }

        return airlineResponseDtos;
    }

    @Transactional
    public AirlineResponseDto updateAirline(Long airlineId, AirlineRequestDto airlineRequestDto) {
        try {
            Airline existingAirline = airlineRepository.findById(airlineId)
                    .orElseThrow(() -> new NotFoundException("Havayolu bulunamadı."));

            existingAirline.setName(airlineRequestDto.getName());
            existingAirline.setCountry(airlineRequestDto.getCountry());

            existingAirline = airlineRepository.save(existingAirline);

            return AirlineResponseDto.builder()
                    .id(existingAirline.getId())
                    .name(existingAirline.getName())
                    .country(existingAirline.getCountry())
                    .build();
        } catch (NotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new BadRequestExcepiton("Havayolları güncellenirken bir hata oluştu.");
        }
    }


    public List<AirlineResponseDto> searchAirlinesByName(String name) {
        List<AirlineResponseDto> airlines = getAllAirlines()
                .stream()
                .filter(airline -> airline.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());

        if (airlines.isEmpty()) {
            throw new NotFoundException("İlgili isimde havayolu bulunamadı.");
        }

        return airlines;
    }

    public void deleteAirline(Long airlineId) {
        try {
            airlineRepository.deleteById(airlineId);
        } catch (EmptyResultDataAccessException ex) {
            throw new NotFoundException("Silmek istediğiniz havayolu bulunamadı.");
        } catch (Exception ex) {
            throw new BadRequestExcepiton("Havayolları silinirken bir hata oluştu.");
        }
    }

    @Transactional
    public FlightResponseDto addFlightToAirline(FlightRequestDto flightRequestDto) {
        Airline airline = airlineRepository.findById(flightRequestDto.getAirlineId())
                .orElseThrow(() -> new NotFoundException("Havayolu bulunamadı."));

        Route route = routeRepository.findById(flightRequestDto.getRouteId())
                .orElseThrow(() -> new NotFoundException("Rota bulunamadı."));

        Flight flight = Flight.builder() //ayri bir methodda yap
                .flightNumber(flightRequestDto.getFlightNumber())
                .totalSeat(flightRequestDto.getTotalSeat())
                .departureAirportCode(flightRequestDto.getDepartureAirportCode())
                .arrivalAirportCode(flightRequestDto.getArrivalAirportCode())
                .airline(airline)
                .route(route)
                .build();

        try {
            flight = flightRepository.save(flight);

            return FlightResponseDto.builder()
                    .airlineId(flight.getId())
                    .flightNumber(flight.getFlightNumber())
                    .totalSeat(flight.getTotalSeat())
                    .departureAirportCode(flight.getDepartureAirportCode())
                    .arrivalAirportCode(flight.getArrivalAirportCode())
                    .routeId(flight.getRoute().getId())
                    .build();
        } catch (Exception ex) {
            throw new BadRequestExcepiton("Uçuş eklenirken bir hata oluştu.");
        }
    }

    public List<FlightResponseDto> getFlightsByAirline(Long airlineId) {
        List<Flight> flights = flightRepository.findByAirlineId(airlineId);
        return flights.stream().map(flight -> FlightResponseDto.builder()
                        .airlineId(airlineId)
                        .flightNumber(flight.getFlightNumber())
                        .departureAirportCode(flight.getDepartureAirportCode())
                        .arrivalAirportCode(flight.getArrivalAirportCode())
                        .build())
                .collect(Collectors.toList());
    }

    public Airline getAirlineById(Long airlineId) {
        return airlineRepository.findById(airlineId).orElse(null);
    }
}
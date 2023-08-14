package io.upschool.service;

import io.upschool.dto.flightDTO.FlightRequestDto;
import io.upschool.dto.flightDTO.FlightResponseDto;
import io.upschool.entity.Airline;
import io.upschool.entity.Flight;
import io.upschool.entity.Route;
import io.upschool.exception.BadRequestExcepiton;
import io.upschool.exception.NotFoundException;
import io.upschool.repository.FlightRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FlightService {

    private final FlightRepository flightRepository;
    private final AirlineService airlineService;
    private final RouteService routeService;


    public List<FlightResponseDto> getAllFlights() {
        try {
            List<Flight> flights = flightRepository.findAll();
            return flights.stream().map(flight -> FlightResponseDto.builder()
                            .airlineId(flight.getId())
                            .flightNumber(flight.getFlightNumber())
                            .routeId(flight.getRoute().getId())
                            .departureAirportCode(flight.getDepartureAirportCode())
                            .arrivalAirportCode(flight.getArrivalAirportCode())
                            .build())
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            throw new BadRequestExcepiton("Uçuşlar alınırken bir hata oluştu.");
        }
    }

    public void bookSeat(Long id){
        Flight flight = findFlightById(id);
        flight.setTotalSeat(flight.getTotalSeat() - 1);
        flightRepository.save(flight);
    }

    public void cancelBook(Long id){
        Flight flight = findFlightById(id);
        flight.setTotalSeat(flight.getTotalSeat() + 1);
        flightRepository.save(flight);
    }
    public Flight findFlightById(Long flightId) {
        try {
            return flightRepository.findById(flightId).orElse(null);
        } catch (Exception ex) {
            throw new BadRequestExcepiton("Uçuş bilgisi alınırken bir hata oluştu.");
        }
    }
    public void deleteFlight(Long flightId) {
        try {
            flightRepository.deleteById(flightId);
        } catch (EmptyResultDataAccessException ex) {
            throw new NotFoundException("Silmek istediğiniz uçuş bulunamadı.");
        } catch (Exception ex) {
            throw new BadRequestExcepiton("Uçuş silinirken bir hata oluştu.");
        }
    }

    @Transactional
    public FlightResponseDto updateFlight(Long flightId, FlightRequestDto flightRequestDto) {
        try {
            Flight existingFlight = flightRepository.findById(flightId)
                    .orElseThrow(() -> new NotFoundException("Uçuş bulunamadı."));

            Airline airline = airlineService.getAirlineById(existingFlight.getAirline().getId());
            Route route = routeService.getRouteById(flightRequestDto.getRouteId());

            if (airline == null || route == null) {
                throw new NotFoundException("Belirtilen havayolu veya rota bulunamadı.");
            }

            existingFlight.setFlightNumber(flightRequestDto.getFlightNumber());
            existingFlight.setDepartureAirportCode(flightRequestDto.getDepartureAirportCode());
            existingFlight.setArrivalAirportCode(flightRequestDto.getArrivalAirportCode());
            existingFlight.setAirline(airline);
            existingFlight.setRoute(route);

            existingFlight = flightRepository.save(existingFlight);

            return FlightResponseDto.builder()
                    .airlineId(existingFlight.getId())
                    .flightNumber(existingFlight.getFlightNumber())
                    .departureAirportCode(existingFlight.getDepartureAirportCode())
                    .arrivalAirportCode(existingFlight.getArrivalAirportCode())
                    .build();
        } catch (NotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new BadRequestExcepiton("Uçuş güncellenirken bir hata oluştu.");
        }
    }
}
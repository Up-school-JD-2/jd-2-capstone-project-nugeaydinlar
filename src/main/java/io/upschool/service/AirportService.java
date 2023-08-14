package io.upschool.service;

import io.upschool.dto.airportDTO.AirportRequestDto;
import io.upschool.dto.airportDTO.AirportResponseDto;
import io.upschool.entity.Airport;
import io.upschool.exception.BadRequestExcepiton;
import io.upschool.exception.NotFoundException;
import io.upschool.repository.AirportRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AirportService {

    private final AirportRepository airportRepository;

    @Transactional
    public AirportResponseDto add(AirportRequestDto airportRequestDto) {
        try {
            Airport newAirport = Airport.builder()
                    .name(airportRequestDto.getName())
                    .city(airportRequestDto.getCity())
                    .country(airportRequestDto.getCountry())
                    .build();
            Airport addedAirport = airportRepository.save(newAirport);
            return AirportResponseDto.builder()
                    .id(addedAirport.getId())
                    .name(addedAirport.getName())
                    .country(addedAirport.getCountry())
                    .build();
        } catch (Exception ex) {
            throw new BadRequestExcepiton("Havalimanları eklenirken bir hata oluştu.");
        }
    }


    public List<AirportResponseDto> getAllAirports() {
        try {
            List<Airport> airports = airportRepository.findAll();
            return airports.stream().map(airport -> AirportResponseDto.builder()
                            .id(airport.getId())
                            .name(airport.getName())
                            .city(airport.getCity())
                            .country(airport.getCountry())
                            .build())
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            throw new BadRequestExcepiton("Havalimanları alınırken bir hata oluştu.");
        }
    }

    @Transactional
    public AirportResponseDto updateAirport(Long airportId, AirportRequestDto airportRequestDto) {
        try {
            Airport existingAirport = airportRepository.findById(airportId)
                    .orElseThrow(() -> new NotFoundException("Havalimanı bulunamadı."));

            existingAirport.setName(airportRequestDto.getName());
            existingAirport.setCountry(airportRequestDto.getCountry());

            existingAirport = airportRepository.save(existingAirport);

            return AirportResponseDto.builder()
                    .id(existingAirport.getId())
                    .name(existingAirport.getName())
                    .city(existingAirport.getCity())
                    .country(existingAirport.getCountry())
                    .build();
        } catch (NotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new BadRequestExcepiton("Havalimanı güncellenirken bir hata oluştu.");
        }
    }
    public List<AirportResponseDto> searchAirportsByName(String name) {
        List<AirportResponseDto> airports = getAllAirports()
                .stream()
                .filter(airport -> airport.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());

        if (airports.isEmpty()) {
            throw new NotFoundException("İlgili isimde havaalanı bulunamadı.");
        }

        return airports;
    }
    public void deleteAirport(Long airportId) {
        try {
            airportRepository.deleteById(airportId);
        } catch (EmptyResultDataAccessException ex) {
            throw new NotFoundException("Silmek istediğiniz havalimanı bulunamadı.");
        } catch (Exception ex) {
            throw new BadRequestExcepiton("Havalimanı silinirken bir hata oluştu.");
        }
    }
}
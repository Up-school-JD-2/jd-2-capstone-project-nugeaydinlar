package io.upschool.dto.flightDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FlightRequestDto {
    private Long airlineId;
    private Long totalSeat;
    private String flightNumber;
    private String departureAirportCode;
    private String arrivalAirportCode;
    private Long routeId;
}
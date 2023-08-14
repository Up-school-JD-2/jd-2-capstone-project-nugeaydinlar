package io.upschool.dto.ticketDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketResponseDto {
    private String passengerName;
    private String ticketNumber;
    private String maskedCreditCardNumber;
    private Long flightId;
    private int totalSeats;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private double price;
    private String seatClass;
}

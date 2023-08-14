package io.upschool.service;

import io.upschool.dto.ticketDTO.TicketRequestDto;
import io.upschool.entity.Flight;
import io.upschool.entity.Ticket;
import io.upschool.exception.NotFoundException;
import io.upschool.repository.TicketRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TicketService {
    private final TicketRepository ticketRepository;
    private final FlightService flightService;


    @Transactional
    public Ticket buyTicket(TicketRequestDto ticketRequestDto) {
        String cleanedCreditCardNumber = cleanCreditCardNumber(ticketRequestDto.getMaskedCreditCardNumber());
        String maskedCreditCardNumber = maskCreditCardNumber(cleanedCreditCardNumber);

        Flight flight = flightService.findFlightById(ticketRequestDto.getFlightId());

        Ticket ticket = Ticket.builder()
                .passengerName(ticketRequestDto.getPassengerName())
                .ticketNumber(ticketRequestDto.getTicketNumber())
                .maskedCreditCardNumber(maskedCreditCardNumber)
                .flight(flight)
                .availableSeats(ticketRequestDto.getTotalSeats())
                .departureTime(ticketRequestDto.getDepartureTime())
                .arrivalTime(ticketRequestDto.getArrivalTime())
                .price(ticketRequestDto.getPrice())
                .seatClass(ticketRequestDto.getSeatClass())
                .status(Ticket.TicketStatus.RESERVED)
                .build();

        flightService.bookSeat(flight.getId());
        return ticketRepository.save(ticket);
    }


    public Ticket findTicketByTicketNumber(String ticketNumber) {
        return ticketRepository.findByTicketNumber(ticketNumber)
                .orElseThrow(() -> new NotFoundException("Bilet bulunamadı."));
    }


    public void cancelTicket(Long ticketId) {
        Ticket ticket = ticketRepository.findByIdAndStatus(ticketId, Ticket.TicketStatus.RESERVED)
                .orElseThrow(() -> new NotFoundException("Belirtilen bilet ID'sine sahip rezerve bilet bulunamadı."));

        ticket.setStatus(Ticket.TicketStatus.CANCELED);
    }

    @Transactional
    public Ticket updateTicket(Long ticketId, TicketRequestDto ticketRequestDto) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new NotFoundException("Bilet bulunamadı."));

        String cleanedCreditCardNumber = cleanCreditCardNumber(ticketRequestDto.getMaskedCreditCardNumber());
        String maskedCreditCardNumber = maskCreditCardNumber(cleanedCreditCardNumber);

        Flight flight = flightService.findFlightById(ticketRequestDto.getFlightId());

        ticket.setPassengerName(ticketRequestDto.getPassengerName());
        ticket.setMaskedCreditCardNumber(maskedCreditCardNumber);
        ticket.setFlight(flight);
        ticket.setAvailableSeats(ticketRequestDto.getTotalSeats());
        ticket.setDepartureTime(ticketRequestDto.getDepartureTime());
        ticket.setArrivalTime(ticketRequestDto.getArrivalTime());
        ticket.setPrice(ticketRequestDto.getPrice());
        ticket.setSeatClass(ticketRequestDto.getSeatClass());

        return ticketRepository.save(ticket);
    }

    private String cleanCreditCardNumber(String creditCardNumber) {
        return creditCardNumber.replace(" ", "").replace("-", "").replace(",", "");
    }

    private String maskCreditCardNumber(String creditCardNumber) {
        if (creditCardNumber == null || creditCardNumber.isEmpty()) {
            return "";
        }

        int visibleLength = 4;
        String visiblePart = creditCardNumber.substring(creditCardNumber.length() - visibleLength);
        String maskedPart = "*".repeat(creditCardNumber.length() - visibleLength);

        return maskedPart + visiblePart;
    }
}
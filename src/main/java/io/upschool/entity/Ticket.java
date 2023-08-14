package io.upschool.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "tickets")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "passenger_name")
    private String passengerName;

    @Column(name = "ticket_number")
    private String ticketNumber;

    @Column(name = "masked_credit_card_number", nullable = false, unique = true)
    private String maskedCreditCardNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flight_id", nullable = false)
    private Flight flight;

    @Column(name = "total_seats")
    private int totalSeats;

    @Column(name = "available_seats")
    private int availableSeats;

    @Column(name = "departure_time")
    private LocalDateTime departureTime;

    @Column(name = "arrival_time")
    private LocalDateTime arrivalTime;

    @Column(name = "price")
    private double price;

    @Column(name = "seat_class")
    private String seatClass;

    public enum TicketStatus {
        RESERVED,
        SOLD,
        CANCELED
    }

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private TicketStatus status;
}
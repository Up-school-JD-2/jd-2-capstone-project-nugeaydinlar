package io.upschool.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "flights")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "flightNumber" , nullable = false, unique = true)
    private String flightNumber;

    @Column(nullable = false, unique = true)
    private Long totalSeat;

    // Uçuş için havayolu şirketi ilişkisi
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "airline_id", nullable = false)
    private Airline airline;

    // Uçuş için rota ilişkisi
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "route_id", nullable = false)
    private Route route;

    // Uçuş için biletleri tanımlamak için ilişki
    @OneToMany(mappedBy = "flight", cascade = CascadeType.ALL)
    private List<Ticket> tickets = new ArrayList<>();

    @Column(nullable = false, unique = true)
    private String departureAirportCode;

    @Column(nullable = false, unique = true)
    private String arrivalAirportCode;

}
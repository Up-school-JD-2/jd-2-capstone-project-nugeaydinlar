package io.upschool.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "routes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private double distance;

    @Column(nullable = false)
    private String name;

    // Rota için havayolu şirketi ilişkisi
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "airline_id", nullable = false)
    private Airline airline;

    // Rota için kalkış ve varış havaalanı ilişkisi
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_airport_id", nullable = false)
    private Airport sourceAirport;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destination_airport_id", nullable = false)
    private Airport destinationAirport;

    // Rota için uçuşları tanımlamak için ilişki
    @OneToMany(mappedBy = "route", cascade = CascadeType.ALL)
    private List<Flight> flights = new ArrayList<>();

}
package io.upschool.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "airports")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Airport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "city", nullable = false, length = 100)
    private String city;

    @Column(name = "country", nullable = false, length = 100)
    private String country;

    // Havaalanına ait rotaları tanımlamak için ilişki
    @OneToMany(mappedBy = "sourceAirport", cascade = CascadeType.ALL)
    private List<Route> sourceRoutes = new ArrayList<>();

    @OneToMany(mappedBy = "destinationAirport", cascade = CascadeType.ALL)
    private List<Route> destinationRoutes = new ArrayList<>();
}
package io.upschool.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "airlines")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Airline {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "country", nullable = false, length = 100)
    private String country;

    // Havayolu şirketine ait uçuşları tanımlamak için ilişki
    @OneToMany(mappedBy = "airline", cascade = CascadeType.ALL)
    private List<Flight> flights = new ArrayList<>();

}
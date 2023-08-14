package io.upschool.dto.airportDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AirportResponseDto {
    private Long id;
    private String name;
    private String country;
    private String city;
}
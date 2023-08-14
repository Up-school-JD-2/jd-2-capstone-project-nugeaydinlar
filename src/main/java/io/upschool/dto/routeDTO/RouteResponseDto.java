package io.upschool.dto.routeDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RouteResponseDto {
    private Long id;
    private double distance;
    private String name;
    public Long airlineId;
    public Long sourceAirportId;
    public Long destinationAirportId;
}

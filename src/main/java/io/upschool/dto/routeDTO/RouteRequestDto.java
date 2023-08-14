package io.upschool.dto.routeDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RouteRequestDto {
    private double distance;
    private String name;
    public Long sourceAirportId;
    public Long destinationAirportId;
    public Long airlineId;
}

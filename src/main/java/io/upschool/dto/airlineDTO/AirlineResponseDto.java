package io.upschool.dto.airlineDTO;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AirlineResponseDto {

    private Long id;
    private String name;
    private String country;
}

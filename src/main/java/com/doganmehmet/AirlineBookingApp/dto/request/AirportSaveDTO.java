package com.doganmehmet.AirlineBookingApp.dto.request;

import com.doganmehmet.AirlineBookingApp.enums.City;
import com.doganmehmet.AirlineBookingApp.enums.Country;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AirportSaveDTO {
    @NotBlank(message = "Airport name cannot be blank")
    private String name;
    @NotNull(message = "City cannot be null")
    private City city;
    @NotNull(message = "Country cannot be null")
    private Country country;
    @NotBlank(message = "IATA code cannot be blank")
    private String iataCode;
}

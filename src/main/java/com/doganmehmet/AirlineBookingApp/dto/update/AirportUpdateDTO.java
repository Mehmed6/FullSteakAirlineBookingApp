package com.doganmehmet.AirlineBookingApp.dto.update;

import com.doganmehmet.AirlineBookingApp.enums.City;
import com.doganmehmet.AirlineBookingApp.enums.Country;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AirportUpdateDTO {
    private long id;
    private String name;
    private City city;
    private Country country;
    private String iataCode;
}

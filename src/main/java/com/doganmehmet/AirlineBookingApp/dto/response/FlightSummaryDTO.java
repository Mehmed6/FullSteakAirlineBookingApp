package com.doganmehmet.AirlineBookingApp.dto.response;

import com.doganmehmet.AirlineBookingApp.enums.FlightStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FlightSummaryDTO {
    private String id;
    private String flightNumber;
    private FlightStatus flightStatus;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime departureTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime arrivalTime;
    private BigDecimal basePrice;
    private AirportDTO departureAirport;
    private AirportDTO arrivalAirport;
}

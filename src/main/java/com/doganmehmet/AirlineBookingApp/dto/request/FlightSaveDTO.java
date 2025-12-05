package com.doganmehmet.AirlineBookingApp.dto.request;

import com.doganmehmet.AirlineBookingApp.enums.FlightStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FlightSaveDTO {

    @NotBlank(message = "Flight number cannot be blank")
    private String flightNumber;
    private FlightStatus flightStatus;
    @NotNull(message = "Departure time cannot be null")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime departureTime;
    @NotNull(message = "Arrival time cannot be null")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime arrivalTime;
    @NotNull(message = "Base price cannot be null")
    @Positive(message = "Base price must be positive")
    private BigDecimal basePrice;
    @NotBlank(message = "Departure airport IATA code cannot be blank")
    private String departureAirportIataCode;
    @NotBlank(message = "Arrival airport IATA code cannot be blank")
    private String arrivalAirportIataCode;

    private Long pilotId;
}

package com.doganmehmet.AirlineBookingApp.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingSaveDTO {

    @NotNull(message = "Flight ID cannot be null")
    private Long flightId;
    @NotEmpty(message = "Passengers list cannot be empty")
    private List<PassengerSaveDTO> passengers;
}

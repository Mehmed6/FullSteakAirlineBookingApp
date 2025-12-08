package com.doganmehmet.AirlineBookingApp.controller;

import com.doganmehmet.AirlineBookingApp.dto.request.FlightSaveDTO;
import com.doganmehmet.AirlineBookingApp.dto.response.FlightDTO;
import com.doganmehmet.AirlineBookingApp.dto.response.Response;
import com.doganmehmet.AirlineBookingApp.dto.update.FlightUpdateDTO;
import com.doganmehmet.AirlineBookingApp.enums.City;
import com.doganmehmet.AirlineBookingApp.enums.Country;
import com.doganmehmet.AirlineBookingApp.enums.FlightStatus;
import com.doganmehmet.AirlineBookingApp.service.FlightService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/flights")
@RequiredArgsConstructor
public class FlightController {

    private final FlightService flightService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'PILOT')")
    public ResponseEntity<Response<?>> createFlight(@Valid @RequestBody FlightSaveDTO flightSaveDTO)
    {
        return ResponseEntity.ok(flightService.createFlight(flightSaveDTO));
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'PILOT')")
    public ResponseEntity<Response<?>> updateFlight(@Valid @RequestBody FlightUpdateDTO flightUpdateDTO)
    {
        return ResponseEntity.ok(flightService.updateFlight(flightUpdateDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<FlightDTO>> getFlightById(@PathVariable Long id)
    {
        return ResponseEntity.ok(flightService.getFlightById(id));
    }

    @GetMapping
    public ResponseEntity<Response<List<FlightDTO>>> getAllFlights()
    {
        return ResponseEntity.ok(flightService.getAllFlights());
    }

    @GetMapping("/search")
    public ResponseEntity<Response<List<FlightDTO>>> searchFlights(
            @RequestParam String departureAirportIataCode,
            @RequestParam String arrivalAirportIataCode,
            @RequestParam(required = false, defaultValue = "SCHEDULED") FlightStatus flightStatus,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate departureDate
    )
    {
        return ResponseEntity.ok(
                flightService.searchFlights(departureAirportIataCode,
                        arrivalAirportIataCode,flightStatus,departureDate));
    }

    @GetMapping("/cities")
    public ResponseEntity<Response<List<City>>> getAllCities()
    {
        return ResponseEntity.ok(flightService.getAllCities());
    }

    @GetMapping("/countries")
    public ResponseEntity<Response<List<Country>>> getAllCountries()
    {
        return ResponseEntity.ok(flightService.getAllCountries());
    }
}

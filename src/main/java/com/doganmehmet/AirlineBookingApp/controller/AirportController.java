package com.doganmehmet.AirlineBookingApp.controller;

import com.doganmehmet.AirlineBookingApp.dto.request.AirportSaveDTO;
import com.doganmehmet.AirlineBookingApp.dto.response.AirportDTO;
import com.doganmehmet.AirlineBookingApp.dto.response.Response;
import com.doganmehmet.AirlineBookingApp.dto.update.AirportUpdateDTO;
import com.doganmehmet.AirlineBookingApp.service.AirportService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/airports")
@RequiredArgsConstructor
public class AirportController {

    private final AirportService airportService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Response<?>> createAirport(@Valid @RequestBody AirportSaveDTO airportSaveDTO)
    {
        return ResponseEntity.ok(airportService.createAirport(airportSaveDTO));
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Response<?>> updateAirport(@RequestBody AirportUpdateDTO airportUpdateDTO)
    {
        return ResponseEntity.ok(airportService.updateAirport(airportUpdateDTO));
    }

    @GetMapping
    public ResponseEntity<Response<List<AirportDTO>>> getAllAirports()
    {
        return ResponseEntity.ok(airportService.getAllAirports());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<AirportDTO>> getAirportById(@PathVariable Long id)
    {
        return ResponseEntity.ok(airportService.getAirportById(id));
    }
}

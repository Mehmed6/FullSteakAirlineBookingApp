package com.doganmehmet.AirlineBookingApp.service.impl;

import com.doganmehmet.AirlineBookingApp.dto.request.FlightSaveDTO;
import com.doganmehmet.AirlineBookingApp.dto.response.FlightDTO;
import com.doganmehmet.AirlineBookingApp.dto.response.Response;
import com.doganmehmet.AirlineBookingApp.dto.update.FlightUpdateDTO;
import com.doganmehmet.AirlineBookingApp.entity.Flight;
import com.doganmehmet.AirlineBookingApp.entity.User;
import com.doganmehmet.AirlineBookingApp.enums.City;
import com.doganmehmet.AirlineBookingApp.enums.Country;
import com.doganmehmet.AirlineBookingApp.enums.FlightStatus;
import com.doganmehmet.AirlineBookingApp.exception.BadRequestException;
import com.doganmehmet.AirlineBookingApp.exception.NotFoundException;
import com.doganmehmet.AirlineBookingApp.repository.AirportRepository;
import com.doganmehmet.AirlineBookingApp.repository.FlightRepository;
import com.doganmehmet.AirlineBookingApp.repository.UserRepository;
import com.doganmehmet.AirlineBookingApp.service.FlightService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class FlightServiceImpl implements FlightService {

    private final FlightRepository flightRepository;
    private final UserRepository userRepository;
    private final AirportRepository airportRepository;
    private final ModelMapper modelMapper;

    private User getPilotOrThrow(Long pilotId)
    {
        var pilot = userRepository.findById(pilotId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        boolean isPilot = pilot.getRoles().stream()
                .anyMatch(role -> role.getName().equalsIgnoreCase("Pilot"));

        if (!isPilot)
            throw new BadRequestException("Assigned user is not a pilot.");

        return pilot;
    }

    @Override
    public Response<?> createFlight(FlightSaveDTO flightSaveDTO)
    {
        if (flightSaveDTO.getArrivalTime().isBefore(flightSaveDTO.getDepartureTime()))
            throw new BadRequestException("Arrival time cannot be before departure time.");

        if (flightRepository.existsByFlightNumber(flightSaveDTO.getFlightNumber()))
            throw new BadRequestException("Flight with the same flight number already exists.");

        if (flightSaveDTO.getDepartureAirportIataCode().equals(flightSaveDTO.getArrivalAirportIataCode()))
            throw new BadRequestException("Departure and arrival airports cannot be the same.");

        var departureAirport = airportRepository.findByIataCode(flightSaveDTO.getDepartureAirportIataCode())
                .orElseThrow(() -> new BadRequestException("Departure airport not found."));

        var arrivalAirport = airportRepository.findByIataCode(flightSaveDTO.getArrivalAirportIataCode())
                .orElseThrow(() -> new BadRequestException("Arrival airport not found."));

        var flight = modelMapper.map(flightSaveDTO, Flight.class);
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setFlightStatus(FlightStatus.SCHEDULED);

        if (flightSaveDTO.getPilotId() != null) {
            flight.setAssignedPilot(getPilotOrThrow(flightSaveDTO.getPilotId()));
        }

        flightRepository.save(flight);

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Flight created successfully.")
                .build();
    }

    @Override
    @Transactional
    public Response<?> updateFlight(FlightUpdateDTO flightUpdateDTO)
    {
        var existingFlight = flightRepository.findById(flightUpdateDTO.getId())
                .orElseThrow(() -> new NotFoundException("Flight not found."));

        if (flightUpdateDTO.getDepartureTime() != null)
            existingFlight.setDepartureTime(flightUpdateDTO.getDepartureTime());
        if (flightUpdateDTO.getArrivalTime() != null)
            existingFlight.setArrivalTime(flightUpdateDTO.getArrivalTime());
        if (flightUpdateDTO.getFlightStatus() != null)
            existingFlight.setFlightStatus(flightUpdateDTO.getFlightStatus());
        if (flightUpdateDTO.getBasePrice() != null)
            existingFlight.setBasePrice(flightUpdateDTO.getBasePrice());

        if (flightUpdateDTO.getPilotId() != null) {
            existingFlight.setAssignedPilot(getPilotOrThrow(flightUpdateDTO.getPilotId()));
        }

        flightRepository.save(existingFlight);

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Flight updated successfully.")
                .build();
    }

    @Override
    public Response<FlightDTO> getFlightById(Long id)
    {
        var flight = flightRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Flight not found."));

        return Response.<FlightDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Flight retrieved successfully.")
                .data(modelMapper.map(flight, FlightDTO.class))
                .build();
    }

    @Override
    public Response<List<FlightDTO>> getAllFlights()
    {
        var flights = flightRepository.findAll(Sort.by(Sort.Direction.DESC, "departureTime"));

        var flightDTOs = flights.stream()
                .map(flight -> modelMapper.map(flight, FlightDTO.class))
                .toList();

        return Response.<List<FlightDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(flightDTOs.isEmpty() ? "No flights found." : "Flights retrieved successfully.")
                .data(flightDTOs)
                .build();
    }

    @Override
    public Response<List<FlightDTO>> searchFlights(String departureAirportIataCode, String arrivalAirportIataCode, FlightStatus flightStatus, LocalDate departureDate)
    {
        var startOfDay = departureDate.atStartOfDay();
        var endOfDay = departureDate.atTime(LocalTime.MAX);

        var flights = flightRepository
                .findByDepartureAirportIataCodeAndArrivalAirportIataCodeAndFlightStatusAndDepartureTimeBetween
                        (departureAirportIataCode,
                                arrivalAirportIataCode,
                                flightStatus, startOfDay, endOfDay);

        var flightDTOs = flights.stream()
                .map(flight ->  modelMapper.map(flight, FlightDTO.class))
                .toList();

        return Response.<List<FlightDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(flightDTOs.isEmpty() ? "No matching flights found." : "Flights retrieved successfully.")
                .data(flightDTOs)
                .build();
    }

    @Override
    public Response<List<City>> getAllCities()
    {
        return Response.<List<City>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Cities retrieved successfully.")
                .data(List.of(City.values()))
                .build();
    }

    @Override
    public Response<List<Country>> getAllCountries()
    {
        return Response.<List<Country>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Countries retrieved successfully.")
                .data(List.of(Country.values()))
                .build();
    }
}

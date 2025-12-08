package com.doganmehmet.AirlineBookingApp.service;

import com.doganmehmet.AirlineBookingApp.dto.request.FlightSaveDTO;
import com.doganmehmet.AirlineBookingApp.dto.response.FlightDTO;
import com.doganmehmet.AirlineBookingApp.dto.response.Response;
import com.doganmehmet.AirlineBookingApp.dto.update.FlightUpdateDTO;
import com.doganmehmet.AirlineBookingApp.enums.City;
import com.doganmehmet.AirlineBookingApp.enums.Country;
import com.doganmehmet.AirlineBookingApp.enums.FlightStatus;

import java.time.LocalDate;
import java.util.List;

public interface FlightService {

    Response<?> createFlight(FlightSaveDTO flightSaveDTO);

    Response<?> updateFlight(FlightUpdateDTO flightUpdateDTO);
    Response<FlightDTO> getFlightById(Long id);
    Response<List<FlightDTO>> getAllFlights();
    Response<List<FlightDTO>> searchFlights(String departureAirportIataCode, String arrivalAirportIataCode, FlightStatus flightStatus, LocalDate departureDate);
    Response<List<City>> getAllCities();
    Response<List<Country>> getAllCountries();
}

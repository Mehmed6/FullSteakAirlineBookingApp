package com.doganmehmet.AirlineBookingApp.service;

import com.doganmehmet.AirlineBookingApp.dto.request.AirportSaveDTO;
import com.doganmehmet.AirlineBookingApp.dto.response.AirportDTO;
import com.doganmehmet.AirlineBookingApp.dto.response.Response;
import com.doganmehmet.AirlineBookingApp.dto.update.AirportUpdateDTO;

import java.util.List;

public interface AirportService {

    Response<?> createAirport(AirportSaveDTO airportSaveDTO);
    Response<?> updateAirport(AirportUpdateDTO airportUpdateDTO);
    Response<List<AirportDTO>> getAllAirports();
    Response<AirportDTO> getAirportById(Long id);

}

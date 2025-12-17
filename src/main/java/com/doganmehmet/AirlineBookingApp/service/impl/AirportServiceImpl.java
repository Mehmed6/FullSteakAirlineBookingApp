package com.doganmehmet.AirlineBookingApp.service.impl;

import com.doganmehmet.AirlineBookingApp.dto.request.AirportSaveDTO;
import com.doganmehmet.AirlineBookingApp.dto.response.AirportDTO;
import com.doganmehmet.AirlineBookingApp.dto.response.Response;
import com.doganmehmet.AirlineBookingApp.dto.update.AirportUpdateDTO;
import com.doganmehmet.AirlineBookingApp.entity.Airport;
import com.doganmehmet.AirlineBookingApp.exception.BadRequestException;
import com.doganmehmet.AirlineBookingApp.exception.NotFoundException;
import com.doganmehmet.AirlineBookingApp.repository.AirportRepository;
import com.doganmehmet.AirlineBookingApp.service.AirportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AirportServiceImpl implements AirportService {

    private final AirportRepository airportRepository;
    private final ModelMapper modelMapper;

    @Override
    public Response<?> createAirport(AirportSaveDTO airportSaveDTO)
    {
        log.info("Inside createAirport()");

        var country = airportSaveDTO.getCountry();
        var city = airportSaveDTO.getCity();

        if (!city.getCountry().equals(country))
            throw new BadRequestException("CITY does not belong to the Country");

        airportRepository.save(modelMapper.map(airportSaveDTO, Airport.class));

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Airport Created Successfully")
                .build();
    }

    @Override
    public Response<?> updateAirport(AirportUpdateDTO airportUpdateDTO)
    {
        var existingAirport = airportRepository.findById(airportUpdateDTO.getId())
                .orElseThrow(() -> new NotFoundException("Airport Not Found"));

        var newCountry = airportUpdateDTO.getCountry();
        if (newCountry != null) {
            var city = airportUpdateDTO.getCity() != null ? airportUpdateDTO.getCity() : existingAirport.getCity();
            if (!city.getCountry().equals(newCountry)) {
                throw new BadRequestException("CITY does not belong to the Country");
            }
            existingAirport.setCountry(newCountry);
        }

        if (StringUtils.hasText(airportUpdateDTO.getName()))
            existingAirport.setName(airportUpdateDTO.getName());

        if (StringUtils.hasText(airportUpdateDTO.getIataCode()))
            existingAirport.setIataCode(airportUpdateDTO.getIataCode());

        airportRepository.save(existingAirport);

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Airport Updated Successfully")
                .build();
    }

    @Override
    public Response<List<AirportDTO>> getAllAirports()
    {
        var airports = airportRepository.findAll().stream()
                .map(airport -> modelMapper.map(airport, AirportDTO.class))
                .toList();

        return Response.<List<AirportDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(airports.isEmpty() ? "No Airports Found" : "Airports Retrieved Successfully")
                .data(airports)
                .build();
    }

    @Override
    public Response<AirportDTO> getAirportById(Long id)
    {
        var airport = airportRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Airport Not Found"));

        return Response.<AirportDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Airport Retrieved Successfully")
                .data(modelMapper.map(airport, AirportDTO.class))
                .build();
    }
}

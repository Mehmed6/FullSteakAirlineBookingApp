package com.doganmehmet.AirlineBookingApp.repository;

import com.doganmehmet.AirlineBookingApp.entity.Flight;
import com.doganmehmet.AirlineBookingApp.enums.FlightStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

    boolean existsByFlightNumber(String flightNumber);

    List<Flight> findByDepartureAirportIataCodeAndArrivalAirportIataCodeAndFlightStatusAndDepartureTimeBetween(
            String departureIataCode,
            String arrivalIataCode,
            FlightStatus flightStatus,
            LocalDateTime startOfDay,
            LocalDateTime endOfDay
    );
}

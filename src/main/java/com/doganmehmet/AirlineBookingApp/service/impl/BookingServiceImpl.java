package com.doganmehmet.AirlineBookingApp.service.impl;

import com.doganmehmet.AirlineBookingApp.dto.request.BookingSaveDTO;
import com.doganmehmet.AirlineBookingApp.dto.response.BookingDTO;
import com.doganmehmet.AirlineBookingApp.dto.response.Response;
import com.doganmehmet.AirlineBookingApp.entity.Booking;
import com.doganmehmet.AirlineBookingApp.entity.Passenger;
import com.doganmehmet.AirlineBookingApp.enums.BookingStatus;
import com.doganmehmet.AirlineBookingApp.enums.FlightStatus;
import com.doganmehmet.AirlineBookingApp.exception.BadRequestException;
import com.doganmehmet.AirlineBookingApp.exception.NotFoundException;
import com.doganmehmet.AirlineBookingApp.repository.BookingRepository;
import com.doganmehmet.AirlineBookingApp.repository.FlightRepository;
import com.doganmehmet.AirlineBookingApp.repository.PassengerRepository;
import com.doganmehmet.AirlineBookingApp.service.BookingService;
import com.doganmehmet.AirlineBookingApp.service.EmailNotificationService;
import com.doganmehmet.AirlineBookingApp.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final UserService userService;
    private final FlightRepository flightRepository;
    private final PassengerRepository passengerRepository;
    private final ModelMapper modelMapper;
    private final EmailNotificationService emailNotificationService;

    private String generateBookingReference()
    {
        return "BR-" + System.currentTimeMillis();
    }

    @Override
    @Transactional
    public Response<?> createBooking(BookingSaveDTO bookingSaveDTO)
    {
        var currentUser = userService.currentUser();
        var flight = flightRepository.findById(bookingSaveDTO.getFlightId())
                .orElseThrow(() -> new NotFoundException("Flight not found"));

        if (flight.getFlightStatus() != FlightStatus.SCHEDULED)
            throw new BadRequestException("You can only book a flight that is scheduled");

        var booking = new Booking();
        booking.setFlight(flight);
        booking.setUser(currentUser);
        booking.setBookingReference(generateBookingReference());
        booking.setBookingStatus(BookingStatus.CONFIRMED);
        booking.setBookingDate(LocalDateTime.now());

        if (bookingSaveDTO.getPassengers() != null && !bookingSaveDTO.getPassengers().isEmpty()) {

            var passengers = bookingSaveDTO.getPassengers().stream()
                    .map(passengerDTO -> {
                        var passenger = modelMapper.map(passengerDTO, Passenger.class);
                        passenger.setBooking(booking);
                        return passenger;
                    })
                    .toList();

            booking.setPassengers(passengers);
        }
        var savedBooking = bookingRepository.save(booking);
        emailNotificationService.sendBookingTicketEmail(savedBooking);

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Booking created successfully")
                .build();
    }

    @Override
    @Transactional
    public Response<?> updateBookingStatus(Long bookingId, BookingStatus status)
    {
        var existingBooking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new NotFoundException("Booking not found"));

        existingBooking.setBookingStatus(status);
        bookingRepository.save(existingBooking);

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Booking status updated successfully")
                .build();
    }

    @Override
    public Response<BookingDTO> getBookingById(Long id)
    {
        var existingBooking = bookingRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Booking not found"));

        var bookingDTO = modelMapper.map(existingBooking, BookingDTO.class);
        return Response.<BookingDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Booking retrieved successfully")
                .data(bookingDTO)
                .build();
    }

    @Override
    public Response<List<BookingDTO>> getAllBookings()
    {
        var bookingsDTOs = bookingRepository.findAll().stream()
                .map(booking -> modelMapper.map(booking, BookingDTO.class))
                .toList();

        return Response.<List<BookingDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(bookingsDTOs.isEmpty() ? "No bookings found" : "Bookings retrieved successfully")
                .data(bookingsDTOs)
                .build();

    }

    @Override
    public Response<List<BookingDTO>> getMyBookings()
    {
        var user = userService.currentUser();
        var bookingsDTOs = bookingRepository.findByUserIdOrderByIdDesc(user.getId())
                .stream()
                .map(booking -> modelMapper.map(booking, BookingDTO.class))
                .toList();

        return Response.<List<BookingDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(bookingsDTOs.isEmpty() ? "You have no bookings" : "Your bookings retrieved successfully")
                .data(bookingsDTOs)
                .build();
    }
}

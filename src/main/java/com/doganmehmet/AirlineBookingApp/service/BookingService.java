package com.doganmehmet.AirlineBookingApp.service;

import com.doganmehmet.AirlineBookingApp.dto.request.BookingSaveDTO;
import com.doganmehmet.AirlineBookingApp.dto.response.BookingDTO;
import com.doganmehmet.AirlineBookingApp.dto.response.Response;
import com.doganmehmet.AirlineBookingApp.enums.BookingStatus;

import java.util.List;

public interface BookingService {

    Response<?> createBooking(BookingSaveDTO bookingSaveDTO);
    Response<?> updateBookingStatus(Long bookingId, BookingStatus status);
    Response<BookingDTO> getBookingById(Long id);
    Response<List<BookingDTO>> getAllBookings();
    Response<List<BookingDTO>> getMyBookings();
}

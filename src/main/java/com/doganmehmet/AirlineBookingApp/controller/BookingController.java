package com.doganmehmet.AirlineBookingApp.controller;

import com.doganmehmet.AirlineBookingApp.dto.request.BookingSaveDTO;
import com.doganmehmet.AirlineBookingApp.dto.response.BookingDTO;
import com.doganmehmet.AirlineBookingApp.dto.response.Response;
import com.doganmehmet.AirlineBookingApp.enums.BookingStatus;
import com.doganmehmet.AirlineBookingApp.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<Response<?>> createBooking(@Valid @RequestBody BookingSaveDTO bookingSaveDTO)
    {
        return ResponseEntity.ok(bookingService.createBooking(bookingSaveDTO));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PILOT')")
    public ResponseEntity<Response<?>> updateBookingStatus(@PathVariable Long id, @RequestParam BookingStatus status)
    {
        return ResponseEntity.ok(bookingService.updateBookingStatus(id, status));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<BookingDTO>> getBookingById(@PathVariable Long id)
    {
        return ResponseEntity.ok(bookingService.getBookingById(id));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'PILOT')")
    public ResponseEntity<Response<List<BookingDTO>>> getAllBookings()
    {
        return ResponseEntity.ok(bookingService.getAllBookings());
    }

    @GetMapping("/me")
    public ResponseEntity<Response<List<BookingDTO>>> getMyBookings()
    {
        return ResponseEntity.ok(bookingService.getMyBookings());
    }
}

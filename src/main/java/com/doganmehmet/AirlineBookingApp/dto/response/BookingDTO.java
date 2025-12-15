package com.doganmehmet.AirlineBookingApp.dto.response;

import com.doganmehmet.AirlineBookingApp.enums.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingDTO {

    private long id;
    private String bookingReference;
    private LocalDateTime bookingDate;
    private BookingStatus bookingStatus;
    private FlightSummaryDTO flight;
    private UserDTO user;
    private List<PassengerDTO> passengers;
}

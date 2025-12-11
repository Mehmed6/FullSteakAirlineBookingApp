package com.doganmehmet.AirlineBookingApp.dto.response;

import com.doganmehmet.AirlineBookingApp.enums.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingSummaryDTO {
    private String bookingReference;
    private LocalDateTime bookingDate;
    private BookingStatus bookingStatus;
}

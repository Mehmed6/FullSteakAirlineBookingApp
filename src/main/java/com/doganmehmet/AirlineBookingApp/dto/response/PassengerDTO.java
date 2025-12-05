package com.doganmehmet.AirlineBookingApp.dto.response;

import com.doganmehmet.AirlineBookingApp.entity.Booking;
import com.doganmehmet.AirlineBookingApp.enums.PassengerType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PassengerDTO {

    private String firstName;
    private String lastName;
    private String passportNumber;
    private PassengerType passengerType;
    private String seatNumber;
    private String specialRequests;
}

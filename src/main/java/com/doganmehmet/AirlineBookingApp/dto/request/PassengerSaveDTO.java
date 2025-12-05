package com.doganmehmet.AirlineBookingApp.dto.request;

import com.doganmehmet.AirlineBookingApp.enums.PassengerType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PassengerSaveDTO {

    private BookingSaveDTO booking;
    private String firstName;
    private String lastName;
    private String passportNumber;
    private PassengerType passengerType;
    private String seatNumber;
    private String specialRequests;
}

package com.doganmehmet.AirlineBookingApp.entity;

import com.doganmehmet.AirlineBookingApp.enums.PassengerType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "passengers")
public class Passenger extends BaseEntity{

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;

    private String firstName;
    private String lastName;
    private String passportNumber;
    @Enumerated(EnumType.STRING)
    private PassengerType passengerType;
    private String seatNumber;
    private String specialRequests;
}

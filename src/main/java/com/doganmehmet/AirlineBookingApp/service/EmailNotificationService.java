package com.doganmehmet.AirlineBookingApp.service;

import com.doganmehmet.AirlineBookingApp.entity.Booking;
import com.doganmehmet.AirlineBookingApp.entity.User;

public interface EmailNotificationService {

    void sendBookingTicketEmail(Booking booking);
    void sendWelcomeEmail(User user);
}

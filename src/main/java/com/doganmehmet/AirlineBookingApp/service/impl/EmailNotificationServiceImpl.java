package com.doganmehmet.AirlineBookingApp.service.impl;

import com.doganmehmet.AirlineBookingApp.entity.Booking;
import com.doganmehmet.AirlineBookingApp.entity.EmailNotification;
import com.doganmehmet.AirlineBookingApp.entity.User;
import com.doganmehmet.AirlineBookingApp.repository.EmailNotificationRepository;
import com.doganmehmet.AirlineBookingApp.service.EmailNotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailNotificationServiceImpl implements EmailNotificationService {

    private final EmailNotificationRepository emailNotificationRepository;
    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    @Value("${frontendLoginUrl}")
    private String frontendLoginUrl;

    @Value("${viewBookingUrl}")
    private String viewBookingUrl;

    private void sendMailOut(String recipientEmail, String subject, String body, boolean isHtml, Booking booking)
    {
        try {
            var mimeMessage = mailSender.createMimeMessage();
            var helper = new MimeMessageHelper(
                    mimeMessage,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name()
            );

            helper.setTo(recipientEmail);
            helper.setSubject(subject);
            helper.setText(body, isHtml);

            log.info("About to send Email...");
            mailSender.send(mimeMessage);
            log.info("Email sent successfully to {}", recipientEmail);
        } catch (Exception ex) {
            log.error("Error while sending email to {}: {}", recipientEmail, ex.getMessage());
        }

        var emailNotification = new EmailNotification();
        emailNotification.setRecipientEmail(recipientEmail);
        emailNotification.setSubject(subject);
        emailNotification.setBody(body);
        emailNotification.setHtml(isHtml);
        emailNotification.setSentAt(LocalDateTime.now());
        emailNotification.setBooking(booking);

        emailNotificationRepository.save(emailNotification);
    }

    @Override
    @Async
    @Transactional
    public void sendBookingTicketEmail(Booking booking)
    {
        log.info("Inside sendBookingTicketEmail()");
        var recipientEmail = booking.getUser().getEmail();
        var subject = "Your Flight Booking Ticket - Reference";
        var templateName = "booking_ticket";

        var templateVariables = new HashMap<String, Object>();
        templateVariables.put("username", booking.getUser().getName());
        templateVariables.put("bookingReference", booking.getBookingReference());
        templateVariables.put("flightNumber", booking.getFlight().getFlightNumber());
        templateVariables.put("departureAirportIataCode", booking.getFlight().getDepartureAirport().getIataCode());
        templateVariables.put("departureAirportName", booking.getFlight().getDepartureAirport().getName());
        templateVariables.put("departureAirportCity", booking.getFlight().getDepartureAirport().getCity());
        templateVariables.put("departureTime", booking.getFlight().getDepartureTime());
        templateVariables.put("arrivalAirportIataCode", booking.getFlight().getArrivalAirport().getIataCode());
        templateVariables.put("arrivalAirportName", booking.getFlight().getArrivalAirport().getName());
        templateVariables.put("arrivalAirportCity", booking.getFlight().getArrivalAirport().getCity());
        templateVariables.put("arrivalTime", booking.getFlight().getArrivalTime());
        templateVariables.put("basePrice", booking.getFlight().getBasePrice());
        templateVariables.put("viewBookingUrl", viewBookingUrl);

        var context = new Context();
        templateVariables.forEach(context::setVariable);
        var emailBody = templateEngine.process(templateName, context);
        sendMailOut(recipientEmail, subject, emailBody, true, booking);
    }

    @Override
    @Async
    @Transactional
    public void sendWelcomeEmail(User user)
    {
        log.info("Sending welcome email to user: {}", user.getEmail());

        var recipientEmail = user.getEmail();
        var subject = "Welcome to Airline Booking App!";
        var templateName = "welcome_user";

        var templateVariables = new HashMap<String, Object>();
        templateVariables.put("username", user.getName());
        templateVariables.put("frontendLoginUrl", frontendLoginUrl);

        var context = new Context();
        templateVariables.forEach(context::setVariable);
        var emailBody = templateEngine.process(templateName, context);

        sendMailOut(recipientEmail, subject, emailBody, true, null);
    }
}

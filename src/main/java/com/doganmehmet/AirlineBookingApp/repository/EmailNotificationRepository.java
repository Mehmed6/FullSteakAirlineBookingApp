package com.doganmehmet.AirlineBookingApp.repository;

import com.doganmehmet.AirlineBookingApp.entity.EmailNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailNotificationRepository extends JpaRepository<EmailNotification, Long> {
}

package com.doganmehmet.AirlineBookingApp.repository;

import com.doganmehmet.AirlineBookingApp.entity.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Long> {


}

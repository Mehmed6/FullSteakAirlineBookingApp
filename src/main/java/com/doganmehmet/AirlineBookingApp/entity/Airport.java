package com.doganmehmet.AirlineBookingApp.entity;

import com.doganmehmet.AirlineBookingApp.enums.City;
import com.doganmehmet.AirlineBookingApp.enums.Country;
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
@Table(name = "airports")
public class Airport extends BaseEntity{

    @Column(unique = true, nullable = false)
    private String name;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private City city;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Country country;
    @Column(unique = true, nullable = false, length = 3)
    private String iataCode;
}

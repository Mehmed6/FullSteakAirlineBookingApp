package com.doganmehmet.AirlineBookingApp.enums;

import lombok.Getter;

@Getter
public enum City {
    NEW_YORK(Country.USA),
    LOS_ANGELES(Country.USA),
    TORONTO(Country.CANADA),
    LONDON(Country.UK),
    BERLIN(Country.GERMANY),
    PARIS(Country.FRANCE),
    ISTANBUL(Country.TURKEY),
    TOKYO(Country.JAPAN),
    SYDNEY(Country.AUSTRALIA),
    MUMBAI(Country.INDIA),
    SAO_PAULO(Country.BRAZIL);

    private final Country country;

    City(Country country)
    {
        this.country = country;
    }
}

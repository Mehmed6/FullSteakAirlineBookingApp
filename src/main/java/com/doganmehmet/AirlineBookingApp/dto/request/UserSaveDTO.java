package com.doganmehmet.AirlineBookingApp.dto.request;

import com.doganmehmet.AirlineBookingApp.entity.Role;
import com.doganmehmet.AirlineBookingApp.enums.AuthMethod;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserSaveDTO {
    private String name;
    private String email;
    private String phoneNumber;
    private AuthMethod authMethod;
    private String providerId;
    private List<Role> roles;
}

package com.doganmehmet.AirlineBookingApp.dto.response;

import com.doganmehmet.AirlineBookingApp.entity.Role;
import com.doganmehmet.AirlineBookingApp.enums.AuthMethod;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String name;
    private String email;
    private String phoneNumber;
    private boolean emailVerified;
    private AuthMethod authMethod;
    private String providerId;
    private boolean active;
    private List<RoleDTO> roles;


}

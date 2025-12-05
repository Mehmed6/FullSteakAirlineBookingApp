package com.doganmehmet.AirlineBookingApp.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleSaveDTO {
    @NotEmpty(message = "Role name must not be empty")
    private String name;
}

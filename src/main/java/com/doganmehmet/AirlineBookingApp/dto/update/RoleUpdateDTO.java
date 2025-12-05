package com.doganmehmet.AirlineBookingApp.dto.update;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleUpdateDTO {
    @NotNull(message = "Role id must not be null")
    private long id;
    @NotEmpty(message = "Role name must not be empty")
    private String name;
}

package com.doganmehmet.AirlineBookingApp.service;

import com.doganmehmet.AirlineBookingApp.dto.request.RoleSaveDTO;
import com.doganmehmet.AirlineBookingApp.dto.response.Response;
import com.doganmehmet.AirlineBookingApp.dto.response.RoleDTO;
import com.doganmehmet.AirlineBookingApp.dto.update.RoleUpdateDTO;

import java.util.List;

public interface RoleService {
    Response<?> save(RoleSaveDTO role);
    Response<?> update(RoleUpdateDTO role);
    Response<List<RoleDTO>> getAll();
}

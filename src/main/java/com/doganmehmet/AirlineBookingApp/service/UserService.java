package com.doganmehmet.AirlineBookingApp.service;

import com.doganmehmet.AirlineBookingApp.dto.response.Response;
import com.doganmehmet.AirlineBookingApp.dto.response.UserDTO;
import com.doganmehmet.AirlineBookingApp.dto.update.UserUpdateDTO;
import com.doganmehmet.AirlineBookingApp.entity.User;

import java.util.List;

public interface UserService {

    User currentUser();
    Response<?> updateMyAccount(UserUpdateDTO userUpdateDTO);
    Response<List<UserDTO>> getAllPilots();
    Response<UserDTO> getAccountDetails();
}

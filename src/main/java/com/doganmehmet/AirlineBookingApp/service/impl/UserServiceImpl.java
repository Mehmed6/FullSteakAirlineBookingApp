package com.doganmehmet.AirlineBookingApp.service.impl;

import com.doganmehmet.AirlineBookingApp.dto.request.UserSaveDTO;
import com.doganmehmet.AirlineBookingApp.dto.response.Response;
import com.doganmehmet.AirlineBookingApp.dto.response.UserDTO;
import com.doganmehmet.AirlineBookingApp.dto.update.UserUpdateDTO;
import com.doganmehmet.AirlineBookingApp.entity.User;
import com.doganmehmet.AirlineBookingApp.exception.NotFoundException;
import com.doganmehmet.AirlineBookingApp.repository.UserRepository;
import com.doganmehmet.AirlineBookingApp.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    @Override
    public User currentUser()
    {
        var email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    @Override
    @Transactional
    public Response<?> updateMyAccount(UserUpdateDTO userUpdateDTO)
    {
        log.info("Inside updateMyAccount()");
        var user = currentUser();

        if (StringUtils.hasText(userUpdateDTO.getName())) {
            user.setName(userUpdateDTO.getName());
        }

        if (StringUtils.hasText(userUpdateDTO.getPassword())) {
            user.setPassword(passwordEncoder.encode(userUpdateDTO.getPassword()));
        }

        if (StringUtils.hasText(userUpdateDTO.getPhoneNumber())) {
            user.setPhoneNumber(userUpdateDTO.getPhoneNumber());
        }

        userRepository.save(user);

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Account Updated Successfully")
                .build();

    }

    @Override
    public Response<List<UserDTO>> getAllPilots()
    {
        log.info("Inside getAllPilots()");
        var pilots = userRepository.findByRoleName("PILOT").stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .toList();

        return Response.<List<UserDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(pilots.isEmpty() ? "No pilots found" : "Pilots retrieved successfully")
                .data(pilots)
                .build();
    }

    @Override
    public Response<UserDTO> getAccountDetails()
    {
        log.info("Inside getAccountDetails()");
        var currentUserDTO = modelMapper.map(currentUser(), UserDTO.class);

        System.out.println(currentUserDTO);
        return Response.<UserDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Successfully retrieved account details")
                .data(currentUserDTO)
                .build();
    }
}

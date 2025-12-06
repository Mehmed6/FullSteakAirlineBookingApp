package com.doganmehmet.AirlineBookingApp.service.impl;

import com.doganmehmet.AirlineBookingApp.dto.request.RoleSaveDTO;
import com.doganmehmet.AirlineBookingApp.dto.response.Response;
import com.doganmehmet.AirlineBookingApp.dto.response.RoleDTO;
import com.doganmehmet.AirlineBookingApp.dto.update.RoleUpdateDTO;
import com.doganmehmet.AirlineBookingApp.entity.Role;
import com.doganmehmet.AirlineBookingApp.exception.NotFoundException;
import com.doganmehmet.AirlineBookingApp.repository.RoleRepository;
import com.doganmehmet.AirlineBookingApp.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
@Slf4j
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    @Override
    public Response<?> save(RoleSaveDTO role)
    {
        log.info("Inside Role save()");
        var newRole = modelMapper.map(role, Role.class);
        newRole.setName(newRole.getName().toUpperCase(Locale.ENGLISH));
        roleRepository.save(newRole);

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Role Created Successfully")
                .build();
    }

    @Override
    public Response<?> update(RoleUpdateDTO role)
    {
        var updatedRole = roleRepository.findById(role.getId())
                .orElseThrow(() -> new NotFoundException("Role Not Found"));

        updatedRole.setName(role.getName().toUpperCase(Locale.ENGLISH));
        roleRepository.save(updatedRole);

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Role updated Successfully")
                .build();
    }

    @Override
    public Response<List<RoleDTO>> getAll()
    {
        var roles = roleRepository.findAll();

        var roleDTOS = roles.stream()
                .map(role -> modelMapper.map(role, RoleDTO.class))
                .toList();

        return Response.<List<RoleDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(roles.isEmpty() ? "No Roles Found" : "Roles Retrieved Successfully")
                .data(roleDTOS)
                .build();
    }
}

package com.doganmehmet.AirlineBookingApp.controller;

import com.doganmehmet.AirlineBookingApp.dto.request.RoleSaveDTO;
import com.doganmehmet.AirlineBookingApp.dto.response.Response;
import com.doganmehmet.AirlineBookingApp.dto.update.RoleUpdateDTO;
import com.doganmehmet.AirlineBookingApp.service.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Response<?>> save(@Valid @RequestBody RoleSaveDTO role)
    {
        return ResponseEntity.ok(roleService.save(role));
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Response<?>> update(@Valid @RequestBody RoleUpdateDTO role)
    {
        return ResponseEntity.ok(roleService.update(role));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Response<?>> getAll()
    {
        return ResponseEntity.ok(roleService.getAll());
    }
}

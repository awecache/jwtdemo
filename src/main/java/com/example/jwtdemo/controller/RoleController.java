package com.example.jwtdemo.controller;

import com.example.jwtdemo.model.RoleDto;
import com.example.jwtdemo.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping("/roles")
    public List<RoleDto> createRole(@RequestBody List<RoleDto> roles) {
        return roleService.createRoles(roles);
    }

    @GetMapping("/roles")
    public List<RoleDto> getAllRoles() {
        return roleService.getAllRoles();
    }

    @GetMapping("/roles/{roleId}")
    public RoleDto getRoleById(@PathVariable Long roleId) {
        return roleService.getRoleById(roleId);
    }

    @DeleteMapping("/roles/{roleId}")
    public void deleteRoleById(@PathVariable Long roleId) {
        roleService.deleteRoleById(roleId);
    }

}

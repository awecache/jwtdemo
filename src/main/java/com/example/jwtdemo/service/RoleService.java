package com.example.jwtdemo.service;

import com.example.jwtdemo.model.RoleDto;

import java.util.List;

public interface RoleService {
    public RoleDto createRole(RoleDto role);

    public List<RoleDto> createRoles(List<RoleDto> roles);

    public List<RoleDto> getAllRoles();

    public RoleDto getRoleById(Long roleId);

    public void deleteRoleById(Long roleId);
}

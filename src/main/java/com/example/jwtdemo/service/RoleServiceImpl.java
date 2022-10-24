package com.example.jwtdemo.service;

import com.example.jwtdemo.entity.RoleEntity;
import com.example.jwtdemo.model.RoleDto;
import com.example.jwtdemo.repository.RoleRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public RoleDto createRole(RoleDto role) {
        RoleEntity roleEntity = new RoleEntity();
        BeanUtils.copyProperties(role, roleEntity);

        RoleEntity savedRole = roleRepository.save(roleEntity);
        BeanUtils.copyProperties(savedRole, role);

        return role;
    }

    @Override
    public List<RoleDto> getAllRoles() {
        List<RoleEntity> roleEntities = roleRepository.findAll();

        return roleEntities.stream()
                .map(this::mapToRoleDto)
                .collect(Collectors.toList());
    }

    private RoleDto mapToRoleDto(RoleEntity entity) {
        RoleDto roleDto = new RoleDto();
        BeanUtils.copyProperties(entity, roleDto);
        return roleDto;
    }

    @Override
    public RoleDto getRoleById(Long roleId) {
        RoleEntity roleEntity = roleRepository.findById(roleId).get();
        return mapToRoleDto(roleEntity);
    }

    @Override
    public void deleteRoleById(Long roleId) {
        roleRepository.deleteById(roleId);
    }
}

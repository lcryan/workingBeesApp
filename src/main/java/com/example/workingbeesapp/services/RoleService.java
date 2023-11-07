package com.example.workingbeesapp.services;

import com.example.workingbeesapp.dtos.RoleDto;
import com.example.workingbeesapp.models.Role;
import com.example.workingbeesapp.repositories.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<RoleDto> getAllRoles() {

        List<RoleDto> roleDtoList = new ArrayList<>();
        for (Role role : roleRepository.findAll()) {
            RoleDto roleDto = new RoleDto();
            roleDto.setRoleName(role.getRoleName());
            roleDtoList.add(roleDto);
        }
        return roleDtoList;
    }
}

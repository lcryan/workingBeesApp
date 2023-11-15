package com.example.workingbeesapp.services;

import com.example.workingbeesapp.dtos.RoleDto;
import com.example.workingbeesapp.models.Role;
import com.example.workingbeesapp.repositories.RoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoleServiceTest {
    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleService roleService;

    @Test
    public void testGetAllRoles() {

        Role roleOne = new Role();
        roleOne.setRoleName("ADMIN");

        Role roleTwo = new Role();
        roleTwo.setRoleName("USER");

        List<Role> testRoles = Arrays.asList(roleOne, roleTwo);

        when(roleRepository.findAll()).thenReturn(testRoles);

        List<RoleDto> result = roleService.getAllRoles();

        verify(roleRepository).findAll();

        assertEquals(2, result.size());
        assertEquals("ADMIN", result.get(0).getRoleName());
        assertEquals("USER", result.get(1).getRoleName());
    }
}

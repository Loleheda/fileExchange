package ru.pinchuk.fileExchange.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.pinchuk.fileExchange.entity.Role;
import ru.pinchuk.fileExchange.repository.RoleRepository;


@ExtendWith(MockitoExtension.class)
public class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleServiceImpl roleService;

    @Test
    public void addRoleTest() {
        String roleName = "USER";
        Role role = roleService.addRole(roleName);
        Assertions.assertEquals(role.getName(), roleName);
    }

    @Test
    public void getRoleByNameTest() {
        String roleName = "USER";
        Role role1 = roleService.addRole(roleName);
        Mockito.when(roleRepository.findRoleByName(roleName)).thenReturn(role1);
        Role role2 = roleService.getRoleByName(roleName);
        Assertions.assertEquals(role1, role2);
    }
}

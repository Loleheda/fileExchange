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
    public void addTest() {
        // Arrange
        String roleName = "USER";

        // Act
        Role role = roleService.addRole(roleName);

        // Assert
        Assertions.assertEquals(role.getName(), roleName);
    }

    @Test
    public void getByNameTest() {
        // Arrange
        Long roleId = 1L;
        String roleName = "USER";
        Role role = new Role(roleId, roleName);

        // Act
        Mockito.when(roleRepository.findByName(roleName)).thenReturn(role);
        Role newRole = roleService.getByName(roleName);

        // Assert
        Assertions.assertNotNull(newRole);
        Assertions.assertEquals(role, newRole);
    }
}

package ru.pinchuk.fileExchange.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
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
        assertEquals(role.getName(), roleName);
    }

    @Test
    public void getByNameTest() {
        // Arrange
        Long roleId = 1L;
        String roleName = "USER";
        Role role = new Role(roleId, roleName);

        // Act
        when(roleRepository.findByName(roleName)).thenReturn(role);
        Role newRole = roleService.getByName(roleName);

        // Assert
        assertNotNull(newRole);
        assertEquals(role, newRole);
    }
}

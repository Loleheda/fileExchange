package ru.pinchuk.fileExchange.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.pinchuk.fileExchange.entity.Role;

@SpringBootTest
class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @Test
    void findRoleByNameTest() {
        String roleName = "ROLE_TEST";
        Role role = roleRepository.save(new Role(roleName));
        Role newRole = roleRepository.findByName(roleName);
        Assertions.assertEquals(role, newRole);
    }
}
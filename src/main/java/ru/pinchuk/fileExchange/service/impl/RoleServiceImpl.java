package ru.pinchuk.fileExchange.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.pinchuk.fileExchange.entity.Role;
import ru.pinchuk.fileExchange.repository.RoleRepository;
import ru.pinchuk.fileExchange.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role getRoleByName(String name) {
        Role role = roleRepository.findRoleByName(name);
        if (role == null) {
            throw new RuntimeException("Role not found: " + name);
        }
        return role;
    }
}

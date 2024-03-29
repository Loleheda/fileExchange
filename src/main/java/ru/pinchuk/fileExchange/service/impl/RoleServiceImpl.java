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
    public Role addRole(String name) {
        Role role = new Role(name);
        roleRepository.save(role);
        return role;
    }

    @Override
    public Role getByName(String name) {
        Role role = roleRepository.findByName(name);
        if (role == null) {
            throw new RuntimeException("Role not found: " + name);
        }
        return role;
    }
}

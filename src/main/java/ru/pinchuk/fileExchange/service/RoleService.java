package ru.pinchuk.fileExchange.service;

import ru.pinchuk.fileExchange.entity.Role;

public interface RoleService {
    Role addRole(String name);
    Role getRoleByName(String name);
}

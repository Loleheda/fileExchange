package ru.pinchuk.fileExchange.service;

import ru.pinchuk.fileExchange.entity.Role;

import java.util.Optional;

public interface RoleService {
    Role getRoleByName(String name);
}

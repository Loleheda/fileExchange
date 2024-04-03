package ru.pinchuk.fileExchange.service;

import ru.pinchuk.fileExchange.entity.Role;
import ru.pinchuk.fileExchange.entity.User;

/**
 * Сервис для управления ролями пользователей.
 */
public interface RoleService {

    /**
     * Добавляет новую роль с указанным именем
     *
     * @param name имя роли
     * @return {@link Role} новая роль
     */
    Role addRole(String name);

    /**
     * Получает роль по её имени
     *
     * @param name имя роли
     * @return {@link Role}
     */
    Role getByName(String name);
}

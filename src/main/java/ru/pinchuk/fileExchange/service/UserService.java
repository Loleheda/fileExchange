package ru.pinchuk.fileExchange.service;

import ru.pinchuk.fileExchange.entity.User;

import java.util.List;

/**
 * Сервис для управления пользователями
 */
public interface UserService  {

    /**
     * Добавляет нового пользователя с заданным логином, паролем и адресом электронной почты
     *
     * @param login     логин пользователя
     * @param password  пароль пользователя
     * @param email     адрес электронной почты пользователя
     * @return {@link User} новый добавленный пользователь
     */
    User addUser(String login, String password, String email);

    /**
     * Добавляет нового администратора с заданным логином, паролем и адресом электронной почты
     *
     * @param login     логин администратора
     * @param password  пароль администратора
     * @param email     адрес электронной почты администратора
     * @return {@link User} новый добавленный администратор
     */
    User addAdmin(String login, String password, String email);

    /**
     * Удаляет пользователя по его логину
     *
     * @param login  логин пользователя для удаления
     * @return  {@link Long} id удаленного пользователя
     */
    Long deleteByLogin(String login);

    /**
     * Получает пользователя по его логину
     *
     * @param login  логин пользователя для поиска
     * @return  {@link User}
     */
    User getByLogin(String login);

    /**
     * Получает пользователя по его адресу электронной почты
     *
     * @param email  адрес электронной почты пользователя
     * @return {@link User}
     */
    User getByEmail(String email);

    /**
     * Получает всех пользователей
     *
     * @return {@link List<User>} список всех пользователей
     */
    List<User> getAllUser();
}

package ru.pinchuk.fileExchange.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * DTO for {@link ru.pinchuk.fileExchange.entity.User}
 */
public class UserDto implements Serializable {
    private final String login;
    private final String password;
    private final String email;

    public UserDto(String login, String password, String email) {
        this.login = login;
        this.password = password;
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto entity = (UserDto) o;
        return Objects.equals(this.login, entity.login) &&
                Objects.equals(this.password, entity.password) &&
                Objects.equals(this.email, entity.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, password, email);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "login = " + login + ", " +
                "password = " + password + ", " +
                "email = " + email + ")";
    }
}
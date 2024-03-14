package ru.pinchuk.fileExchange.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * DTO for {@link ru.pinchuk.fileExchange.entity.File}
 */
public class FileDto implements Serializable {
    private final UserDto user;
    private final String url;

    public FileDto(UserDto user, String url) {
        this.user = user;
        this.url = url;
    }

    public UserDto getUser() {
        return user;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileDto entity = (FileDto) o;
        return Objects.equals(this.user, entity.user) &&
                Objects.equals(this.url, entity.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, url);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "user = " + user + ", " +
                "url = " + url + ")";
    }
}
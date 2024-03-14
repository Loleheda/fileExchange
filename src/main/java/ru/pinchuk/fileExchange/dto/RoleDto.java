package ru.pinchuk.fileExchange.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * DTO for {@link ru.pinchuk.fileExchange.entity.Role}
 */
public class RoleDto implements Serializable {
    private final String name;

    public RoleDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoleDto entity = (RoleDto) o;
        return Objects.equals(this.name, entity.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "name = " + name + ")";
    }
}
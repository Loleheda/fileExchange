package ru.pinchuk.fileExchange.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * DTO for {@link ru.pinchuk.fileExchange.entity.RequestStatus}
 */
public class RequestStatusDto implements Serializable {
    private final String name;

    public RequestStatusDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestStatusDto entity = (RequestStatusDto) o;
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
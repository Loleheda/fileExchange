package ru.pinchuk.fileExchange.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * DTO for {@link ru.pinchuk.fileExchange.entity.Request}
 */
public class RequestDto implements Serializable {
    private final UserDto recipient;
    private final RequestStatusDto status;
    private final FileDto file;

    public RequestDto(UserDto recipient, RequestStatusDto status, FileDto file) {
        this.recipient = recipient;
        this.status = status;
        this.file = file;
    }

    public UserDto getRecipient() {
        return recipient;
    }

    public RequestStatusDto getStatus() {
        return status;
    }

    public FileDto getFile() {
        return file;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestDto entity = (RequestDto) o;
        return Objects.equals(this.recipient, entity.recipient) &&
                Objects.equals(this.status, entity.status) &&
                Objects.equals(this.file, entity.file);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recipient, status, file);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "recipient = " + recipient + ", " +
                "status = " + status + ", " +
                "file = " + file + ")";
    }
}
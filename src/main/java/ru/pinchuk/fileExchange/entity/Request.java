package ru.pinchuk.fileExchange.entity;

import javax.persistence.*;

@Entity
@Table(name = "requests")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name="recipient", referencedColumnName="id", nullable = false)
    private User recipient;
    @OneToOne
    @JoinColumn(name="status", referencedColumnName="id", nullable = false)
    private RequestStatus status;
    @OneToOne
    @JoinColumn(name="file", referencedColumnName="id", nullable = false)
    private File file;

    public Request() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getRecipient() {
        return recipient;
    }

    public void setRecipient(User recipient) {
        this.recipient = recipient;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}

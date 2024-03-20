package ru.pinchuk.fileExchange.entity;

import javax.persistence.*;

@Entity
@Table(name = "files")
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name= "owner", referencedColumnName="id", nullable = false)
    private User owner;
    @Column(name = "name", nullable = false)
    private String name;

    public File() {
    }

    public File(User owner, String name) {
        this.owner = owner;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "File{" +
                "id=" + id +
                ", user=" + owner +
                ", name='" + name + '\'' +
                '}';
    }
}

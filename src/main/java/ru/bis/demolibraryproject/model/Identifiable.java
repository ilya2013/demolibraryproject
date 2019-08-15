package ru.bis.demolibraryproject.model;

import lombok.Data;
import lombok.Getter;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;


@Data
@Getter
@MappedSuperclass
public class Identifiable implements Comparable<Identifiable>, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime creationTime;

    private LocalDateTime updateTime;

    @PrePersist
    protected void onCreate() {
        creationTime = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updateTime = LocalDateTime.now();
    }

    public int compareTo(Identifiable o) {
        if (this.id == null) return -1;
        return this.id.compareTo(o.getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != Hibernate.getClass(o)) return false;
        Identifiable that = (Identifiable) o;
        if (id == null || that.getId() == null) return false;
        return id.equals(that.getId());
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}


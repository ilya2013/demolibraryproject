package ru.bis.demolibraryproject.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;


@Entity
@Getter
@Setter
public class Book extends Identifiable {
    @Column(name = "title", length = 64, nullable = false)
    private String title;
    @Column(name = "language", length = 64, nullable = false)
    private String language;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    @JsonBackReference
    private Author author;

    public Book(String title, String language, Author author) {
        this.title = title;
        this.language = language;
        this.author = author;
    }

    public Book() {
    }
}

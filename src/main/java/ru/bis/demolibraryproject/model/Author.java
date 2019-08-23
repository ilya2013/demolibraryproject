package ru.bis.demolibraryproject.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Getter
@Setter
@Data
public class Author extends Identifiable {
    @Column(name = "fullName", nullable = false)
    private String fullName;
    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
    @Cascade(value = org.hibernate.annotations.CascadeType.ALL)
    @JsonManagedReference
    private List<Book> books;

    public Author() {
    }

    public Author(String fullName) {
        this.fullName = fullName;
    }

    public Long getId(){return super.getId();};

    @Override
    public String toString() {
        return "Author{" +
        "id=" + this.getId() +
                ", fullName='" + fullName + '\'' +
                '}';
    }
}

package ru.bis.demolibraryproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.bis.demolibraryproject.model.Book;

public interface BookRepository extends  JpaRepository <Book, Long>, JpaSpecificationExecutor<Book> {
    Book findByTitle(String title);
}

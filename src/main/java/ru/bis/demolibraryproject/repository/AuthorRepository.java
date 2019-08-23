package ru.bis.demolibraryproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.bis.demolibraryproject.model.Author;

public interface AuthorRepository extends  JpaRepository <Author, Long>, JpaSpecificationExecutor<Author> {
    Author findByFullName(String title);
}
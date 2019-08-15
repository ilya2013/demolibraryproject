package ru.bis.demolibraryproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;
import ru.bis.demolibraryproject.model.Author;
import java.util.List;

public interface AuthorRepository extends  JpaRepository <Author, Long>, JpaSpecificationExecutor<Author> {
}
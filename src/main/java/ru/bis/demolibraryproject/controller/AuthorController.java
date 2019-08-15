package ru.bis.demolibraryproject.controller;

import com.sun.xml.bind.v2.TODO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bis.demolibraryproject.model.Author;
import ru.bis.demolibraryproject.model.Book;
import ru.bis.demolibraryproject.model.DTO.AuthorDTO;
import ru.bis.demolibraryproject.repository.AuthorRepository;
import ru.bis.demolibraryproject.specification.AuthorSpecificationsBuilder;
import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorController {
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private AuthorDTO authorDTO;

    @PostMapping(value = "/{name}")
    public ResponseEntity add(@PathVariable String name) {
        Author author = new Author(name);
        authorRepository.save(author);
        return new ResponseEntity<>("SUCCES", HttpStatus.OK);
    }

    @PostMapping( consumes = "application/json")
    public ResponseEntity addByJSON(@RequestBody Author author) throws Exception {
        if (author.getId() != null || author.getFullName().isEmpty()) {
            return new ResponseEntity("Check par", HttpStatus.BAD_REQUEST);
        }
        authorRepository.save(author);
        return new ResponseEntity<>(author, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable Long id) {
        Author author = null;
        author = authorRepository.findById(id).orElse(null);
        if (author == null) {
            return new ResponseEntity("Author not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(author, HttpStatus.OK);
    }

    @GetMapping("/name/{fullname}")
    public ResponseEntity get(@PathVariable String fullname) {
        Author author = null;
        AuthorSpecificationsBuilder authorSB= new AuthorSpecificationsBuilder();
        Specification specification = authorSB.with("fullName", ":", fullname).build();
        List<Author> authors = authorRepository.findAll(specification);
        if (authors.isEmpty()) {
            return new ResponseEntity("Author not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(authors, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        Author author = null;
        author = authorRepository.findById(id).orElse(null);
        if (author == null) {
            return new ResponseEntity("Author not found", HttpStatus.OK);
        }
        authorRepository.delete(author);
        return new ResponseEntity("Deleted", HttpStatus.OK);
    }

    @PutMapping("/{id}/{fullname}")
    public ResponseEntity update(@PathVariable Long id, @PathVariable String fullname) {
        String oldFullName;
        Author author = null;
        author = authorRepository.findById(id).orElse(null);
        if (author == null) {
            return new ResponseEntity("Author not found", HttpStatus.NOT_FOUND);
        }
        oldFullName = author.getFullName();
        author.setFullName(fullname);
        authorRepository.save(author);
        return new ResponseEntity(oldFullName + " -> " + author.getFullName(), HttpStatus.OK);
    }

    @PutMapping(consumes = "application/json")
    public ResponseEntity update(@RequestBody Author author) {
        Author oldAuthor;
        if (author.getId() == null || author.getFullName().isEmpty()) {
            return new ResponseEntity("Check par", HttpStatus.BAD_REQUEST);
        }
        oldAuthor = authorRepository.findById(author.getId()).orElse(null);
        if (oldAuthor == null) {
            return new ResponseEntity("Author not found", HttpStatus.NOT_FOUND);
        }
        author = authorRepository.save(author);
        return new ResponseEntity(author, HttpStatus.OK);
    }

}

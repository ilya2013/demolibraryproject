package ru.bis.demolibraryproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bis.demolibraryproject.model.Author;
import ru.bis.demolibraryproject.model.Book;
import ru.bis.demolibraryproject.repository.AuthorRepository;
import ru.bis.demolibraryproject.repository.BookRepository;
import ru.bis.demolibraryproject.specification.BookSpecificationsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/books")
public class BookController {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;

    @PostMapping("/{title}/{language}/{authorid}")
    public ResponseEntity add(@PathVariable String title, @PathVariable String language, @PathVariable Long authorid) {
        Author author = authorRepository.findById(authorid).orElse(null);
        if (author == null) return new ResponseEntity("No author", HttpStatus.NOT_FOUND);
        Book book = new Book(title, language, author);
        book = bookRepository.save(book);
        return new ResponseEntity(book, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> get(@PathVariable Long id) {
        Book book = null;
        book = bookRepository.findById(id).orElse(null);
        if (book == null) {
            return new ResponseEntity("Book not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(book, HttpStatus.OK);
    }

    /**
     * Поиск автора книги по её названию.
     *
     * @param title Заголовок
     * @return
     */
    @GetMapping("/authorName/{title}")
    public ResponseEntity<Book> get(@PathVariable String title) {
        BookSpecificationsBuilder bookSB = new BookSpecificationsBuilder();
        Specification specification = bookSB.with("title", ":", title).build();
        List<Book> books = bookRepository.findAll(specification);
        if (books.isEmpty()) {
            return new ResponseEntity("Book not found", HttpStatus.NOT_FOUND);
        }
        List<Author> authors = books.stream()
                .map(b -> b.getAuthor())
                .distinct()
                .collect(Collectors.toList());
        String authorsNames = authors.stream().
                map(a -> a.getFullName() + " ").
                collect(Collectors.joining());
        return new ResponseEntity(authorsNames, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        Book book = null;
        book = bookRepository.findById(id).orElse(null);
        if (book == null) {
            return new ResponseEntity("Book not found", HttpStatus.OK);
        }
        bookRepository.delete(book);
        return new ResponseEntity("Deleted", HttpStatus.OK);
    }

    @PutMapping("/{id}/{newTitle}")
    public ResponseEntity update(@PathVariable Long id, @PathVariable String newTitle) {
        String oldTitle;
        Book book = bookRepository.findById(id).orElse(null);
        if (book == null) {
            return new ResponseEntity("Book not found", HttpStatus.NOT_FOUND);
        }
        oldTitle = book.getTitle();
        book.setTitle(newTitle);
        bookRepository.save(book);
        return new ResponseEntity(oldTitle + " -> " + book.getTitle(), HttpStatus.OK);
    }
}

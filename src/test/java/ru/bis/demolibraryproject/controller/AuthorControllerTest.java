package ru.bis.demolibraryproject.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import ru.bis.demolibraryproject.model.Author;
import ru.bis.demolibraryproject.repository.AuthorRepository;
import ru.bis.demolibraryproject.repository.BookRepository;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional(readOnly = false)
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-author-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class AuthorControllerTest {
    @Autowired
    private AuthorController controller;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void ifGetauthorWithBooksWhenGetAuthorWithBooks() throws Exception {
        String expected = "{'id':1,'creationTime':null,'updateTime':null,'fullName':'test2','books':[{'id':1,'creationTime':null,'updateTime':null,'title':'Live','language':'rus'}]}";
        this.mockMvc.perform((get("/authors/1")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expected));
    }

    @Test
    public void ifDeleteAuthorWithBooksWhenAuthorAndBooksDeleted() throws Exception {
        this.mockMvc.perform((delete("/authors/1")))
                .andExpect(status().isOk());
        this.mockMvc.perform((get("/authors/1")))
                .andExpect(status().isNotFound());
        this.mockMvc.perform((get("/books/1")))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteUnexistingAuthor_Not_Found() throws Exception {
        this.mockMvc.perform((delete("/authors/2")))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteExistingAuthor_OK() throws Exception {
        boolean expected = false;
        this.mockMvc.perform((delete("/authors/1")))
                .andExpect(status().isOk());
        this.mockMvc.perform((get("/authors/1")))
                .andExpect(status().isNotFound());
        assertThat(authorRepository.existsById(1L), is(expected));
    }

    @Test
    public void postNewAuthor_OK() throws Exception {
        StringBuilder sb = new StringBuilder();
        String authorName = "Fedr";
        this.mockMvc.perform(post(sb.append("/authors/")
                .append(authorName)
                .toString()))
                .andExpect(status().isOk());
        Author author = authorRepository.findByFullName(authorName);
        assertThat(author.getFullName(), is(authorName));
    }
}
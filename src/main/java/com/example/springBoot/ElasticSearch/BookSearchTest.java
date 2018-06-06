package com.example.springBoot.ElasticSearch;

import com.example.springBoot.ElasticSearch.entity.Book;
import com.example.springBoot.ElasticSearch.service.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BookSearchTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void testSave() {

        Book book = new Book("Elasticsearch Basics", "Rambabu Posa", new Date("23-FEB-2017"));
        Book testBook = bookRepository.save(book);

        assertNotNull(testBook.getId());

    }
}

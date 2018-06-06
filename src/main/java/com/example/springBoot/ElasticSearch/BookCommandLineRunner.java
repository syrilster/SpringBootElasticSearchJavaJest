package com.example.springBoot.ElasticSearch;

import com.example.springBoot.ElasticSearch.entity.Book;
import com.example.springBoot.ElasticSearch.service.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class BookCommandLineRunner implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(BookCommandLineRunner.class);

    @Autowired
    private BookRepository bookRepository;

    @Override
    public void run(String... args) throws Exception {
        List<Book> bookList = new ArrayList<>();

        bookList.add(new Book("Elasticsearch Basics", "Syril Sadasivan", new Date()));
        bookList.add(new Book("Homo Sapiens", "yuval noah harari", new Date("21-MAR-2017")));
        bookList.add(new Book("Homo Deus: A Brief History of Tomorrow ", "yuval noah harari", new Date("21-MAR-2019")));
        bookList.add(new Book("Meditations", "Marcus Aurelius", new Date("01-APR-2017")));
        bookList.add(new Book("YouTube", "MKBHD", new Date()));

        for (Book book : bookList) {
            bookRepository.save(book);
        }

        List<Book> bookListFromDB = bookRepository.findAll();

        BookESDirectory bookESDirectory = new BookESDirectory();
        bookESDirectory.init();
        bookESDirectory.index(bookListFromDB.get(0));
        bookESDirectory.search("Elasticsearch Basics");
    }
}

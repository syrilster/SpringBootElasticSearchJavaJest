package com.example.springBoot.ElasticSearch.service;

import com.example.springBoot.ElasticSearch.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}

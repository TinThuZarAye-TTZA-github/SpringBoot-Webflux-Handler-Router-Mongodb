package com.springboot.webflux.repository;

import com.springboot.webflux.entities.Book;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends ReactiveMongoRepository<Book,Integer> {
}

package com.springboot.webflux.handler;

import com.springboot.webflux.entities.Book;
import com.springboot.webflux.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;

@Service
public class BookHandler {

    @Autowired
    private BookRepository repo;

    public Mono<ServerResponse> getAllBook(ServerRequest request) {
        Flux<Book> bookFlux = repo.findAll();
        return ServerResponse.ok().body(bookFlux,Book.class);
    }

    public Mono<ServerResponse> getBookById(ServerRequest request) {
        int id = Integer.parseInt(request.pathVariable("id"));
        Mono<Book> bookMono = repo.findById(id);
        return bookMono.flatMap(book -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(fromValue(book)));
    }

    public Mono<ServerResponse> saveBook(ServerRequest request) {
        Mono<Book> bookMono = request.bodyToMono(Book.class);
        return bookMono.flatMap(
                book -> ServerResponse.status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(repo.save(book),Book.class)
        );
    }

    public Mono<ServerResponse> updateBookById(ServerRequest request) {
        int id = Integer.parseInt(request.pathVariable("id"));
        Mono<Book> bookId = repo.findById(id);
        Mono<Book> book = request.bodyToMono(Book.class);
        return book.zipWith(bookId,(b, bId) ->
                new Book(bId.getBookId(),b.getName(),b.getPrice()))
                .flatMap(b -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(repo.save(b),Book.class));

    }

    public Mono<ServerResponse> deleteBookById(ServerRequest request) {
        int id = Integer.parseInt(request.pathVariable("id"));
        Mono<Book> bookMono = repo.findById(id);
        return bookMono.flatMap(book -> ServerResponse.ok().body(repo.delete(book), Book.class));
    }
}

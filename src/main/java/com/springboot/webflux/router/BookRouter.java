package com.springboot.webflux.router;

import com.springboot.webflux.handler.BookHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class BookRouter {
    @Autowired
    private BookHandler handler;

    @Bean
    public RouterFunction<ServerResponse> router() {
        return RouterFunctions.route()
                .GET("/book", handler::getAllBook)
                .GET("/book/{id}", handler::getBookById)
                .POST("/book", handler::saveBook)
                .PUT("/book/{id}", handler::updateBookById)
                .DELETE("/book/{id}", handler::deleteBookById)
                .build();
    }

}

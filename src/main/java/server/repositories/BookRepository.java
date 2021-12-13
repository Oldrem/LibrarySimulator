package server.repositories;

import org.springframework.data.repository.CrudRepository;
import server.model.Book;

import java.util.List;

public interface BookRepository extends CrudRepository<Book, Long> {
    List<Book> findByAuthorNameContainsIgnoreCase(String name);
    List<Book> findByGenreContainingIgnoreCase(String name);
    List<Book> findByAuthorName(String author);
    List<Book> findByAnnotationContainingIgnoreCase(String value);
    List<Book> findByNameContainingIgnoreCase(String value);
}
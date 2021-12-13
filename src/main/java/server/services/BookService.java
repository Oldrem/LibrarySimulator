package server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.model.Book;
import server.repositories.BookRepository;

import java.util.*;

@Service
public class BookService {
    private final BookRepository libraryRepository;

    @Autowired
    public BookService(BookRepository libraryRepository) {
        this.libraryRepository = libraryRepository;
    }

    public List<Book> findByKeywords(String keywords, String publisherName){
        Set<Book> books = new HashSet<>();
        String[] keys = keywords.split("%20");
        for(String key: keys) {
            books.addAll(libraryRepository.findByNameContainingIgnoreCase(key));
            books.addAll(libraryRepository.findByAuthorNameContainsIgnoreCase(key));
            books.addAll(libraryRepository.findByGenreContainingIgnoreCase(key));
            books.forEach(book -> book.setAnnotation(null));
            books.addAll(libraryRepository.findByAnnotationContainingIgnoreCase(key));
        }
        books.removeIf(p -> !p.getPublisherName().equals(publisherName));
        return new ArrayList<>(books);
    }

    public List<Book> findByName(String name, String publisherName){
        List<Book> books = new ArrayList<>();
        books.addAll(libraryRepository.findByNameContainingIgnoreCase(name));
        books.removeIf(p -> !p.getPublisherName().equals(publisherName));
        return new ArrayList<>(books);
    }

    public List<Book> findByAuthor(String author, String publisherName){
        List<Book> books = new ArrayList<>();
        books.addAll(libraryRepository.findByAuthorName(author));
        books.removeIf(p -> !p.getPublisherName().equals(publisherName));
        return new ArrayList<>(books);
    }
}

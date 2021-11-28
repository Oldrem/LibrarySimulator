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

    public List<Book> findByKeywords(String keywords){
        Set<Book> books = new HashSet<>();
        String[] keys = keywords.split("%20");
        for(String key: keys) {
            books.addAll(libraryRepository.findByNameContainingIgnoreCase(key));
            books.addAll(libraryRepository.findByAuthorNameContainsIgnoreCase(key));
            books.addAll(libraryRepository.findByGenreContainingIgnoreCase(key));
            books.forEach(book -> book.setAnnotation(null));
            books.addAll(libraryRepository.findByAnnotationContainingIgnoreCase(key));
        }
        return new ArrayList<>(books);
    }

    public List<Book> find(Boolean nameFlag, Boolean authorFlag, Boolean keyWordsFlag, String value) {
        if (nameFlag) {
            return libraryRepository.findByNameContainingIgnoreCase(value);
        }
        if (authorFlag) {
            return libraryRepository.findByAuthorName(value);
        }
        return Collections.emptyList();
    }
}

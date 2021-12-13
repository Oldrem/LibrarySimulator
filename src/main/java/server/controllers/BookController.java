package server.controllers;

import org.apache.tomcat.jni.Local;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import server.model.Book;
import server.model.User;
import server.repositories.BookRepository;
import server.services.BookService;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
public class BookController
{
    private BookRepository bookRepository;
    private BookService service;

    public BookController(BookRepository bookRepository, BookService service) {
        this.bookRepository = bookRepository;
        this.service = service;
    }

    @GetMapping("/books")
    Collection<Book> books() {
        return (Collection<Book>) bookRepository.findAll();
    }

    @GetMapping("/books/name")
    public ResponseEntity<Iterable<Book>> getBookByName(@RequestParam(name = "name") String name, @RequestParam (name = "login") String login){
        List<Book> list = service.findByName(name, login);
        return new ResponseEntity<Iterable<Book>>(list, HttpStatus.OK);
    }

    @GetMapping("/books/author")
    public ResponseEntity<Iterable<Book>> getBookByAuthor(@RequestParam(name = "author") String author, @RequestParam (name = "login") String login){
        List<Book> list = service.findByAuthor(author, login);
        return new ResponseEntity<Iterable<Book>>(list, HttpStatus.OK);
    }

    @GetMapping("/books/keywords")
    public ResponseEntity<Iterable<Book>> getBookByKeywords(@RequestParam(name = "keywords") String keywords, @RequestParam (name = "login") String login){
        List<Book> list = service.findByKeywords(keywords, login);
        return new ResponseEntity<Iterable<Book>>(list, HttpStatus.OK);
    }

    @PostMapping("/books/")
    public Book createBook(@RequestParam(name = "name") String name,
                    @RequestParam(name = "author") String author,
                    @RequestParam(name = "genre") String genre,
                    @RequestParam(name = "date") String date,
                    @RequestParam(name = "annotation") String annotation,
                    @RequestParam(name = "isbn") String isbn,
                    @RequestParam(name = "publisher") String publisher
    ) {
        LocalDate parsedDate;
        try{
            parsedDate = LocalDate.parse(date);
        }
        catch (Exception e){
            System.out.println("Couldn't parse date");
            parsedDate = LocalDate.now();
        }
        Book book = new Book(name, author, genre, parsedDate, annotation, isbn, publisher);
        formatBookRequest(book);
        return bookRepository.save(book);
    }

    public void formatBookRequest(Book book) {
        book.setName(book.getName().replaceAll("%20", " "));
        book.setAuthorName(book.getAuthorName().replaceAll("%20", " "));
        book.setGenre(book.getGenre().replaceAll("%20", " "));
        book.setAnnotation(book.getAnnotation().replaceAll("%20", " "));
        book.setIsbn(book.getIsbn().replaceAll("%20", " "));
        book.setPublisherName(book.getPublisherName().replaceAll("%20", " "));
    }
}
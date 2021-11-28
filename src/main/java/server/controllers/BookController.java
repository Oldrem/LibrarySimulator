package server.controllers;

import org.apache.tomcat.jni.Local;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.model.Book;
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

    @GetMapping("/books/{id}")
    ResponseEntity<?> getBook(@PathVariable Long id) {
        Optional<Book> book = bookRepository.findById(id);
        return book.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/books/name")
    public ResponseEntity<Iterable<Book>> getBookByName(@RequestParam(name = "name") String name){
        List<Book> list = bookRepository.findByNameContainingIgnoreCase(name);
        return new ResponseEntity<Iterable<Book>>(list, HttpStatus.OK);
    }

    @GetMapping("/books/author")
    public ResponseEntity<Iterable<Book>> getBookByAuthor(@RequestParam(name = "author") String author){
        List<Book> list = bookRepository.findByAuthorName(author);
        return new ResponseEntity<Iterable<Book>>(list, HttpStatus.OK);
    }

    @GetMapping("/books/keywords")
    public ResponseEntity<Iterable<Book>> getBookByKeywords(@RequestParam(name = "keywords") String keywords){
        List<Book> list = service.findByKeywords(keywords);
        return new ResponseEntity<Iterable<Book>>(list, HttpStatus.OK);
    }

    @PostMapping("/books/")
    public Book createBook(@RequestParam(name = "name") String name,
                    @RequestParam(name = "author") String author,
                    @RequestParam(name = "genre") String genre,
                    @RequestParam(name = "date") String date,
                    @RequestParam(name = "annotation") String annotation,
                    @RequestParam(name = "isbn") String isbn
    ) {
        LocalDate parsedDate;
        try{
            parsedDate = LocalDate.parse(date);
        }
        catch (Exception e){
            System.out.println("Couldn't parse date");
            parsedDate = LocalDate.now();
        }
        return bookRepository.save(new Book(name, author, genre,
                parsedDate, annotation, isbn));
    }

    @PutMapping("/books/{id}")
    ResponseEntity<Book> updateBook(@PathVariable Long id, @Valid @RequestBody Book book)
    {
        if (!bookRepository.existsById(id))
            throw new RuntimeException("Invalid BookId");
        book.setBookId(id);
        Book result = bookRepository.save(book);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id) {
        bookRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
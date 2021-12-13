package server.model;

import javax.persistence.*;
import java.time.LocalDate;


@Entity
@Table (name="book")
public class Book {
    @Id @GeneratedValue
    private long bookId;
    private String name;
    private String authorName;
    private String genre;
    private LocalDate publishDate;
    private String annotation;
    private String isbn;
    private String publisherName;

    public Book(){}

    public Book(String name, String authorName, String genre, LocalDate publishDate, String annotation, String isbn, String publisherName) {
        this.name = name;
        this.authorName = authorName;
        this.genre = genre;
        this.publishDate = publishDate;
        this.annotation = annotation;
        this.isbn = isbn;
        this.publisherName = publisherName;
    }

    public long getBookId() {
        return bookId;
    }

    public void setBookId(long id) {
        this.bookId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public LocalDate getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(LocalDate publishDate) {
        this.publishDate = publishDate;
    }

    public String getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }
}

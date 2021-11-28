package client.commands;

import client.model.Book;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Objects;

public class BookCommand {

    private final String API_URL = "http://localhost:8080/books/";

    public BookCommand(){}

    public void addBook(Book book){
        RestTemplate restTemplate = new RestTemplate();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(API_URL)
                .queryParam("name", book.getName())
                .queryParam("author", book.getAuthorName())
                .queryParam("genre", book.getGenre())
                .queryParam("date", book.getPublishDate())
                .queryParam("annotation", book.getAnnotation())
                .queryParam("isbn", book.getIsbn());
        try {
            ResponseEntity<Book> responseEntity = restTemplate.exchange(builder.toUriString(),
                    HttpMethod.POST, null,
                    new ParameterizedTypeReference<Book>(){});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void findBookByParam(String paramName, String paramValue){
        RestTemplate restTemplate = new RestTemplate();
        String url = API_URL + paramName;
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).queryParam(paramName, paramValue);
        try {
            ResponseEntity<List<Book>> response = restTemplate.exchange(builder.toUriString(),
                    HttpMethod.GET, null,
                    new ParameterizedTypeReference<List<Book>>(){});
            printSearchResults(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void printSearchResults(ResponseEntity<List<Book>> response){
        System.out.println("Search result: ");
        for(Book book : Objects.requireNonNull(response.getBody())) {
            System.out.println("Name: " + book.getName());
            System.out.println("Author: " + book.getAuthorName());
            System.out.println("Genre: " + book.getGenre());
            if (book.getAnnotation() != null) {
                System.out.println("Annotation: " + book.getAnnotation());
            }
            System.out.println("Publish date: " + book.getPublishDate());
            System.out.println("ISBN: " + book.getIsbn());
            System.out.println("===============================");
        }
    }
}

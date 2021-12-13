package client.commands;

import client.model.Book;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Objects;

public class BookCommand {

    private final String API_URL = "http://localhost:8080/books/";

    public BookCommand(){}

    public void addBook(Book book, String login, String password){
        RestTemplate restTemplate = new RestTemplate();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(API_URL)
                .queryParam("name", book.getName())
                .queryParam("author", book.getAuthorName())
                .queryParam("genre", book.getGenre())
                .queryParam("date", book.getPublishDate())
                .queryParam("annotation", book.getAnnotation())
                .queryParam("isbn", book.getIsbn())
                .queryParam("publisher", book.getPublisherName());
        try {
            ResponseEntity<Book> responseEntity = restTemplate.exchange(builder.toUriString(),
                    HttpMethod.POST,
                    new HttpEntity<>(createAuthorizationHeader(login, password)),
                    new ParameterizedTypeReference<Book>(){});
        } catch (Exception e) {
            System.out.println("Error sending request to the server.");
        }
    }

    public void findBookByParam(String paramName, String paramValue, String login, String password){
        RestTemplate restTemplate = new RestTemplate();
        String url = API_URL + paramName;
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam(paramName, paramValue)
                .queryParam("login", login);
        try {
            ResponseEntity<List<Book>> response = restTemplate.exchange(builder.toUriString(),
                    HttpMethod.GET,
                    new HttpEntity<>(createAuthorizationHeader(login, password)),
                    new ParameterizedTypeReference<List<Book>>(){});
            printSearchResults(response);
        } catch (Exception e) {
            System.out.println("Error sending request to the server.");
        }
    }

    public void printSearchResults(ResponseEntity<List<Book>> response){
        System.out.println("Search results: ");
        System.out.println("-------------------------------------------");
        for(Book book : Objects.requireNonNull(response.getBody())) {
            System.out.println("Name: " + book.getName());
            System.out.println("Author: " + book.getAuthorName());
            System.out.println("Genre: " + book.getGenre());
            if (book.getAnnotation() != null) {
                System.out.println("Annotation: " + book.getAnnotation());
            }
            System.out.println("Publish date: " + book.getPublishDate());
            System.out.println("ISBN: " + book.getIsbn());
            System.out.println();
            System.out.println("Added by: " + book.getPublisherName());
            System.out.println("-------------------------------------------");
        }
    }

    HttpHeaders createAuthorizationHeader(String username, String password){
        return new HttpHeaders() {{
            String auth = username + ":" + password;
            byte[] encodedAuth = Base64.encodeBase64(
                    auth.getBytes(Charset.forName("UTF-8")) );
            String authHeader = "Basic " + new String( encodedAuth );
            set( "Authorization", authHeader );
        }};
    }
}

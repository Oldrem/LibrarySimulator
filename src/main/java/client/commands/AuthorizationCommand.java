package client.commands;

import client.model.Book;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.nio.charset.Charset;
import java.sql.SQLOutput;

public class AuthorizationCommand {
    private final String API_URL = "http://localhost:8080/";

    public AuthorizationCommand(){}

    public boolean registerUser(String login, String password){
        RestTemplate restTemplate = new RestTemplate();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(API_URL + "register")
                .queryParam("login", login)
                .queryParam("password", password);
        try {
            ResponseEntity responseEntity = restTemplate.exchange(builder.toUriString(),
                    HttpMethod.POST,
                    null,
                    new ParameterizedTypeReference<Book>(){});
            if (responseEntity.getStatusCode() == HttpStatus.BAD_REQUEST){
                System.out.println("This user already exists.");
                return false;
            }
        } catch (Exception e) {
            System.out.println("Error sending request to the server.");
            return false;
        }
        return true;
    }

    public boolean loginUser(String login, String password){
        RestTemplate restTemplate = new RestTemplate();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(API_URL + "login")
                .queryParam("login", login);
        try {
            ResponseEntity responseEntity = restTemplate.exchange(builder.toUriString(),
                    HttpMethod.GET,
                    new HttpEntity<>(createAuthorizationHeader(login, password)),
                    new ParameterizedTypeReference<Book>(){});
        } catch (Exception e) {
            System.out.println("Incorrect login information.");
            return false;
        }
        return true;
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

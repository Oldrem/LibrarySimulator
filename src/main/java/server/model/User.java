package server.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;

@Entity
@Table(name = "users")
@JsonIgnoreProperties({"password","hibernateLazyInitializer", "handler"})
public class User
{
    @Id
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = new BCryptPasswordEncoder().encode(password);
    }

    public User() {}

    public String getUsername() {
        return username;
    }

    public void setUsername(String login) {
        this.username = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
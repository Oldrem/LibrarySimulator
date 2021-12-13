package server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import server.model.User;
import server.repositories.UserRepository;

@Component
public class DatabaseLoader implements CommandLineRunner {

    private final UserRepository users;

    @Autowired
    public DatabaseLoader(UserRepository users) {
        this.users = users;
    }

    @Override
    public void run(String... strings) throws Exception
    {
        User admin = users.save(new User("admin", "123"));
        User oleg = users.save(new User("oleg", "oleg"));
    }
}

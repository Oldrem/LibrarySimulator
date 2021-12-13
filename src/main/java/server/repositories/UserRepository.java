package server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import server.model.User;

public interface UserRepository extends JpaRepository<User, String> {
}

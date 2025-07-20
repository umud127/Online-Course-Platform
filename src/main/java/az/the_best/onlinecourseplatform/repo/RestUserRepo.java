package az.the_best.onlinecourseplatform.repo;

import az.the_best.onlinecourseplatform.entities.roles.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RestUserRepo extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    Optional<List<User>> findBy();
}

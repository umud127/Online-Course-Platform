package az.the_best.onlinecourseplatform.repo;

import az.the_best.onlinecourseplatform.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
}

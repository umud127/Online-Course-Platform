package az.the_best.onlinecourseplatform.repo;

import az.the_best.onlinecourseplatform.entities.RefreshToken;
import az.the_best.onlinecourseplatform.entities.roles.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RestRefreshTokenRepo extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(String token);

    void deleteByUser(User user);

}

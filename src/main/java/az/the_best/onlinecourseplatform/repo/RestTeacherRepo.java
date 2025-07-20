package az.the_best.onlinecourseplatform.repo;

import az.the_best.onlinecourseplatform.entities.roles.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RestTeacherRepo extends JpaRepository<Teacher, Long> {

    Optional<Teacher> findByUserId(Long userId);

}

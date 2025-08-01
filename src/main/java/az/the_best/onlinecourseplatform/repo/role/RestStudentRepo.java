package az.the_best.onlinecourseplatform.repo.role;

import az.the_best.onlinecourseplatform.entities.roles.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RestStudentRepo extends JpaRepository<Student, Long> {

    boolean existsByUserId(Long userId);

    Optional<Student> findByUserId(Long userId);
}

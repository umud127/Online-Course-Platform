package az.the_best.onlinecourseplatform.repo.course;

import az.the_best.onlinecourseplatform.entities.course.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestEnrollmentRepo extends JpaRepository<Enrollment, Long> {

    boolean existsByStudentIdAndCourseId(Long userId, Long courseId);
}

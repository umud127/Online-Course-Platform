package az.the_best.onlinecourseplatform.repo;

import az.the_best.onlinecourseplatform.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepo extends JpaRepository<Course, Long> {
}

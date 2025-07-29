package az.the_best.onlinecourseplatform.repo.course;

import az.the_best.onlinecourseplatform.entities.course.Course;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RestCourseRepo extends JpaRepository<Course, Long> {
    List<Course> findByNameStartingWithIgnoreCase(String name);

    List<Course> findTop5ByOrderByClickCountDesc();

    @Transactional
    @Modifying
    @Query("Update Course c Set c.clickCount = c.clickCount + 1 Where c.id = :id")
    void increaseClickCount(Long id);

    Optional<List<Course>> findByTeacherId(Long teacherId);
}

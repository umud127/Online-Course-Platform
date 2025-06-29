package az.the_best.onlinecourseplatform.repo;

import az.the_best.onlinecourseplatform.entities.Course;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepo extends JpaRepository<Course, Long> {
    List<Course> findByNameStartingWithIgnoreCase(String name);

    List<Course> findTop5ByOrderByClickCountDesc();

    @Transactional
    @Modifying
    @Query("Update Course c Set c.clickCount = c.clickCount + 1 Where c.id = :id")
    void increaseClickCount(Long id);
}

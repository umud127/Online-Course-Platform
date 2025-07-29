package az.the_best.onlinecourseplatform.repo.role.request;

import az.the_best.onlinecourseplatform.entities.roles.request.Teacher_Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestTeacherRequestRepo extends JpaRepository<Teacher_Request,Long> {

    boolean existsByUserId_Id(Long userId);
}

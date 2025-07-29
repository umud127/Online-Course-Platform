package az.the_best.onlinecourseplatform.repo.role.response;

import az.the_best.onlinecourseplatform.entities.roles.response.Admin_Response;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestAdminResponseRepo extends JpaRepository<Admin_Response,Long> {
}

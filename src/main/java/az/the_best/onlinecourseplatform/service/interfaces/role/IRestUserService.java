package az.the_best.onlinecourseplatform.service.interfaces.role;

import az.the_best.onlinecourseplatform.dto.response.DTOCourse;
import az.the_best.onlinecourseplatform.dto.response.DTOUser;
import az.the_best.onlinecourseplatform.dto.iu.DTOUserIU;
import az.the_best.onlinecourseplatform.entities.BaseEntity;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface IRestUserService {

    BaseEntity<?> getUserById(Long id);

    void editUser(@ModelAttribute @Valid DTOUserIU dtoUserIU, @PathVariable Long id);

    void deleteUser(Long id);

    BaseEntity<?> getCoursesThatUserEnrolled(Long id);

    BaseEntity<?> checkTeacherStatus(String authHeader);
}

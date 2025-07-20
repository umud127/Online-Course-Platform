package az.the_best.onlinecourseplatform.service;

import az.the_best.onlinecourseplatform.dto.DTOCourse;
import az.the_best.onlinecourseplatform.dto.DTOUser;
import az.the_best.onlinecourseplatform.dto.IU.DTOUserIU;
import az.the_best.onlinecourseplatform.entities.BaseEntity;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface IRestUserService {

    BaseEntity<DTOUser> getUserById(Long id);

    void editUser(@ModelAttribute @Valid DTOUserIU dtoUserIU, @PathVariable Long id);

    void deleteUser(Long id);

    BaseEntity<List<DTOCourse>> getCoursesThatUserEnrolled(Long id);
}

package az.the_best.onlinecourseplatform.controller.interfaces.role;

import az.the_best.onlinecourseplatform.dto.response.DTOCourse;
import az.the_best.onlinecourseplatform.dto.response.DTOUser;
import az.the_best.onlinecourseplatform.dto.iu.DTOUserIU;
import az.the_best.onlinecourseplatform.entities.BaseEntity;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

public interface IRestUserController {

    BaseEntity<?> getUser(@RequestHeader("Authorization") String authHeader);

    void editUser(@ModelAttribute @Valid DTOUserIU dtoUserIU, @RequestHeader("Authorization") String authHeader);

    void deleteUser(@RequestHeader("Authorization") String authHeader);

    BaseEntity<?> getCoursesThatUserEnrolled(@RequestHeader("Authorization") String authHeader);

    BaseEntity<?> checkTeacherStatus(String authHeader);

}

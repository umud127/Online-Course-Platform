package az.the_best.onlinecourseplatform.controller;

import az.the_best.onlinecourseplatform.dto.DTOCourse;
import az.the_best.onlinecourseplatform.dto.DTOUser;
import az.the_best.onlinecourseplatform.dto.IU.DTOUserIU;
import az.the_best.onlinecourseplatform.entities.BaseEntity;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IRestUserController {

    BaseEntity<DTOUser> getUser(@RequestHeader("Authorization") String authHeader);

    void editUser(@ModelAttribute @Valid DTOUserIU dtoUserIU, @RequestHeader("Authorization") String authHeader);

    void deleteUser(@RequestHeader("Authorization") String authHeader);

    BaseEntity<List<DTOCourse>> getCoursesThatUserEnrolled(@RequestHeader("Authorization") String authHeader);

}

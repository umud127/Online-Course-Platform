package az.the_best.onlinecourseplatform.controller.impl.role;

import az.the_best.onlinecourseplatform.controller.interfaces.role.IRestUserController;
import az.the_best.onlinecourseplatform.dto.response.DTOCourse;
import az.the_best.onlinecourseplatform.dto.response.DTOUser;
import az.the_best.onlinecourseplatform.dto.iu.DTOUserIU;
import az.the_best.onlinecourseplatform.entities.BaseEntity;
import az.the_best.onlinecourseplatform.exception.MessageType;
import az.the_best.onlinecourseplatform.jwt.JWTService;
import az.the_best.onlinecourseplatform.service.interfaces.role.IRestUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping(path = "/user")
@RequiredArgsConstructor
public class RestUserControllerIMPL implements IRestUserController {

    private final IRestUserService userService;
    private final JWTService jWTService;


    @GetMapping(path = "/me")
    @Override
    public BaseEntity<?> getUser(@RequestHeader(value = "Authorization", required = false) String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return BaseEntity.notOk(MessageType.UNAUTHORIZED, "Authorization header missing or invalid");
        }

        String token;
        try {
            token = authHeader.substring(7);
            Long userId = jWTService.extractId(token);
            return userService.getUserById(userId);
        } catch (Exception e) {
            return BaseEntity.notOk(MessageType.UNAUTHORIZED, "Invalid token");
        }
    }


    @PutMapping(path = "/me/update")
    @Override
    public void editUser(DTOUserIU dtoUserIU, @RequestHeader("Authorization") String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            Long userId = jWTService.extractId(token);

            userService.editUser(dtoUserIU,userId);
        }
    }


    @DeleteMapping(path = "me/delete")
    @Override
    public void deleteUser(@RequestHeader("Authorization") String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            Long userId = jWTService.extractId(token);

            userService.deleteUser(userId);
            return;
        }

        System.out.println("something went wrong(deleting user)(authorization header)");
    }

    //user must be checked here
    @GetMapping(path = "/getEnrolledCourses")
    @Override
    public BaseEntity<?> getCoursesThatUserEnrolled(@RequestHeader("Authorization") String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            Long userId = jWTService.extractId(token);

            return userService.getCoursesThatUserEnrolled(userId);
        }

        return null;
    }

    @GetMapping(path = "/checkTeacherStatus")
    @Override
    public BaseEntity<?> checkTeacherStatus(@RequestHeader(value = "Authorization") String authHeader) {
        return userService.checkTeacherStatus(authHeader);
    }

}

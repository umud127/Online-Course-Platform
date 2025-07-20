package az.the_best.onlinecourseplatform.controller.impl;

import az.the_best.onlinecourseplatform.controller.IRestUserController;
import az.the_best.onlinecourseplatform.dto.DTOCourse;
import az.the_best.onlinecourseplatform.dto.DTOUser;
import az.the_best.onlinecourseplatform.dto.IU.DTOUserIU;
import az.the_best.onlinecourseplatform.entities.BaseEntity;
import az.the_best.onlinecourseplatform.exception.MessageType;
import az.the_best.onlinecourseplatform.jwt.JWTService;
import az.the_best.onlinecourseplatform.service.IRestUserService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping(path = "/user")
public class RestUserControllerIMPL implements IRestUserController {

    private final IRestUserService userService;
    private final JWTService jWTService;

    public RestUserControllerIMPL(IRestUserService userService, JWTService jWTService) {
        this.userService = userService;
        this.jWTService = jWTService;
    }


    @GetMapping(path = "/me")
    @Override
    public BaseEntity<DTOUser> getUser(@RequestHeader(value = "Authorization", required = false) String authHeader) {
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
    public BaseEntity<List<DTOCourse>> getCoursesThatUserEnrolled(@RequestHeader("Authorization") String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            Long userId = jWTService.extractId(token);

            return userService.getCoursesThatUserEnrolled(userId);
        }

        return null;
    }


}

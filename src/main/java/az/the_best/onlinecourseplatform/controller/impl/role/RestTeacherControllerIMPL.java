package az.the_best.onlinecourseplatform.controller.impl.role;

import az.the_best.onlinecourseplatform.controller.interfaces.role.IRestTeacherController;
import az.the_best.onlinecourseplatform.entities.BaseEntity;
import az.the_best.onlinecourseplatform.service.interfaces.role.IRestTeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/teacher")
public class RestTeacherControllerIMPL implements IRestTeacherController {

    private final IRestTeacherService teacherService;

    @GetMapping(path = "/myCourses")
    @Override
    public BaseEntity<?> getMyCourses(@RequestHeader("Authorization")String authHeader) {
        return teacherService.getMyCourses(authHeader.substring(7));
    }
}

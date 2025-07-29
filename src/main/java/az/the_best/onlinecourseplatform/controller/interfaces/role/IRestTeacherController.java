package az.the_best.onlinecourseplatform.controller.interfaces.role;

import az.the_best.onlinecourseplatform.entities.BaseEntity;

public interface IRestTeacherController {

    BaseEntity<?> getMyCourses(String authHeader);
}

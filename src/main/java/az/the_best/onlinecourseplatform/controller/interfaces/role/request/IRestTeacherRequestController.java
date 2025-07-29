package az.the_best.onlinecourseplatform.controller.interfaces.role.request;

import az.the_best.onlinecourseplatform.dto.iu.DTOTeacherRequestIU;
import org.springframework.web.multipart.MultipartFile;

public interface IRestTeacherRequestController {

    void createRequest(DTOTeacherRequestIU request, String authHeader);

    boolean checkTeacherHaveRequest(Long teacherId);

}

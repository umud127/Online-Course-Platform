package az.the_best.onlinecourseplatform.service.interfaces.role.request;

import az.the_best.onlinecourseplatform.dto.iu.DTOTeacherRequestIU;
import org.springframework.web.multipart.MultipartFile;

public interface IRestTeacherRequestService {

    void createRequest(DTOTeacherRequestIU request, String token);

    boolean checkTeacherHaveRequest(Long userId);
}

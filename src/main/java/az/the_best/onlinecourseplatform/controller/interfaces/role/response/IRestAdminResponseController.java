package az.the_best.onlinecourseplatform.controller.interfaces.role.response;

import az.the_best.onlinecourseplatform.dto.response.DTOAdminResponse;
import az.the_best.onlinecourseplatform.entities.BaseEntity;

public interface IRestAdminResponseController {

    BaseEntity<?> giveResponse(DTOAdminResponse response);

    BaseEntity<?> getTeacherResponseByRequestId(Long teacherRequestId);

    BaseEntity<?> getAllRequests(int page, int size);
}

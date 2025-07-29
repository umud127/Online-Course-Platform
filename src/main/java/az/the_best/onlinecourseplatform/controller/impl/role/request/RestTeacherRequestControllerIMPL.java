package az.the_best.onlinecourseplatform.controller.impl.role.request;

import az.the_best.onlinecourseplatform.controller.interfaces.role.request.IRestTeacherRequestController;
import az.the_best.onlinecourseplatform.dto.iu.DTOTeacherRequestIU;
import az.the_best.onlinecourseplatform.service.interfaces.role.request.IRestTeacherRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/teacher_request")
public class RestTeacherRequestControllerIMPL implements IRestTeacherRequestController {

    private final IRestTeacherRequestService teacherRequestService;

    @PostMapping(path = "/request", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Override
    public void createRequest(
            @ModelAttribute DTOTeacherRequestIU request,
            @RequestHeader("Authorization") String authHeader) {
        teacherRequestService.createRequest(request, authHeader.substring(7));
    }

    @GetMapping(path = "/checkTeacherHaveRequest/{id}")
    @Override
    public boolean checkTeacherHaveRequest(@PathVariable(value = "id") Long teacherId) {
        return teacherRequestService.checkTeacherHaveRequest(teacherId);
    }


}

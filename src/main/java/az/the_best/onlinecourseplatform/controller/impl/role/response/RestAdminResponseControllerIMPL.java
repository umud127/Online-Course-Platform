package az.the_best.onlinecourseplatform.controller.impl.role.response;

import az.the_best.onlinecourseplatform.controller.interfaces.role.response.IRestAdminResponseController;
import az.the_best.onlinecourseplatform.dto.response.DTOAdminResponse;
import az.the_best.onlinecourseplatform.entities.BaseEntity;
import az.the_best.onlinecourseplatform.service.interfaces.role.response.IRestAdminResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/adminResponse")
@RequiredArgsConstructor
public class RestAdminResponseControllerIMPL implements IRestAdminResponseController {

    private final IRestAdminResponseService adminResponseService;


    @PostMapping(path = "response")
    public BaseEntity<?> giveResponse(@RequestBody DTOAdminResponse responseDTO) {
        return adminResponseService.giveResponse(responseDTO);
    }

    @GetMapping("/getTeacherResponse/{teacherRequestId}")
    public BaseEntity<?> getTeacherResponseByRequestId(@PathVariable Long teacherRequestId) {
        return adminResponseService.getTeacherResponseByRequestId(teacherRequestId);
    }

    @GetMapping(path = "/getAllRequests")
    public BaseEntity<?> getAllRequests(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return adminResponseService.getAllRequests(page, size);
    }
}

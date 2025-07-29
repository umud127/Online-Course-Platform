package az.the_best.onlinecourseplatform.dto.response;

import az.the_best.onlinecourseplatform.security.Decision;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DTOAdminResponse {
    private Long userId;
    private Long teacherRequestId;
    private Decision decision;
    private String messageToTeacher; // yalnız reject olsa istifadə olunur
}

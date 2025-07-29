package az.the_best.onlinecourseplatform.dto.iu;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DTOTeacherRequestIU {

    private String description;

    private String messageToAdmin;

    private String subjects;

    private MultipartFile file;
}

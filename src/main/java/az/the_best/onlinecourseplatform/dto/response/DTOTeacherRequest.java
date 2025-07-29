package az.the_best.onlinecourseplatform.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DTOTeacherRequest {
        private Long id;
        private String description;
        private String messageToAdmin;
        private String subjects;
        private String photoUrl;
        private Long userId;
}

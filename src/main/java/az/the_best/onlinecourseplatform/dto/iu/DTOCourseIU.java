package az.the_best.onlinecourseplatform.dto.iu;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DTOCourseIU {

    @NotNull(message = "Name can't be null")
    @NotBlank(message = "Name can't be empty(Try at least 4 letter)")
    @Size(min = 4, max = 50, message = "Name must be between 4 and 50 letters")
    private String name;

    @NotNull(message = "Description can't be null")
    @NotBlank(message = "Description can't be empty(Try at least 20 letter)")
    @Size(min = 20, max = 1000, message = "Name must be between 20 and 1000 letters")
    private String description;

    private List<DTOChapterIU> chapters;
}

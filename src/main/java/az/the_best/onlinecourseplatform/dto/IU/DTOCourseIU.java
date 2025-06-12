package az.the_best.onlinecourseplatform.dto.IU;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class DTOCourseIU {

    @NotNull(message = "Name can't be null")
    @NotBlank(message = "Name can't be empty(Try at least 4 letter)")
    @Size(min = 4, max = 50, message = "Name must be between 4 and 50 letters")
    String name;

    @NotNull(message = "Description can't be null")
    @NotBlank(message = "Description can't be empty(Try at least 20 letter)")
    @Size(min = 20, max = 1000, message = "Name must be between 20 and 1000 letters")
    String description;
}

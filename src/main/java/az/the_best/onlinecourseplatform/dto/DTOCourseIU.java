package az.the_best.onlinecourseplatform.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DTOCourseIU {

    @NotNull(message = "Name is required")
    String name;

    @NotNull(message = "Description is required")
    String description;
}

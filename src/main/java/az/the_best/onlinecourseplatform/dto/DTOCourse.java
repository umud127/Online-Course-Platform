package az.the_best.onlinecourseplatform.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigInteger;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DTOCourse {

    private Long id;
    private String name;
    private String description;
    private String coverPhoto;
    private BigInteger clickCount;
}

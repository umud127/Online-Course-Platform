package az.the_best.onlinecourseplatform.dto.response;

import az.the_best.onlinecourseplatform.dto.iu.DTOChapterIU;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigInteger;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DTOCourse {

    private Long id;
    private String name;
    private String description;
    private String coverPhoto;
    private BigInteger clickCount;
    private List<DTOChapterIU> chapters;
}

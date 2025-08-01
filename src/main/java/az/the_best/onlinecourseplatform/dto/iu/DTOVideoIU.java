package az.the_best.onlinecourseplatform.dto.iu;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DTOVideoIU {
    private String title;

    private int order;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String url;
}

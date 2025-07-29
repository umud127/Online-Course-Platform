package az.the_best.onlinecourseplatform.dto.iu;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DTOChapterIU {
    private String title;

    private int order;

    private List<DTOVideoIU> videos;
}

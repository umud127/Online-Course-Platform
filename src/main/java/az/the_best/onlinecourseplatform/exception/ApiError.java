package az.the_best.onlinecourseplatform.exception;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiError<T> {

    private Integer status;

    private Exception<T> exception;
}

package az.the_best.onlinecourseplatform.exception;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ApiError<T> {

    private String id;

    private Date date;

    private T error;
}

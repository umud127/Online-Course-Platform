package az.the_best.onlinecourseplatform.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Exception<T> {

    private String id;

    private String path;

    private String hostName;

    private Date createDate;

    private T error;
}

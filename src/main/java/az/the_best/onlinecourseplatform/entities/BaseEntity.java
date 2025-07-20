package az.the_best.onlinecourseplatform.entities;

import az.the_best.onlinecourseplatform.exception.ErrorMessage;
import az.the_best.onlinecourseplatform.exception.MessageType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseEntity<T> {

    private boolean result;

    private ErrorMessage errorMessage;

    private T data;

    public static <T> BaseEntity<T> ok(T data) {
        BaseEntity<T> baseEntity = new BaseEntity<>();

        baseEntity.setResult(true);
        baseEntity.setErrorMessage(null);
        baseEntity.setData(data);

        return baseEntity;
    }

    public static <T> BaseEntity<T> notOk(MessageType messageType, String id) {
        BaseEntity<T> baseEntity = new BaseEntity<>();

        baseEntity.setResult(false);
        baseEntity.setData(null);
        baseEntity.setErrorMessage(new ErrorMessage(messageType,id));

        return baseEntity;
    }
}

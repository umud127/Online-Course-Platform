package az.the_best.onlinecourseplatform.exception;

public class BaseException extends RuntimeException {

    public BaseException(ErrorMessage message) {
        super(message.prepereMessage());
    }
}

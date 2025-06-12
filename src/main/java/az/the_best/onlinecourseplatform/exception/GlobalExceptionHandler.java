package az.the_best.onlinecourseplatform.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import java.util.*;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity< ApiError < Map<String, List<String>> > > handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        Map<String, List<String>> errorMap = new HashMap<>();

        for (ObjectError error : exception.getBindingResult().getAllErrors()) {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();

            errorMap.computeIfAbsent(fieldName, key -> new ArrayList<>()).add(errorMessage);
        }

        return ResponseEntity.badRequest().body(buildApiError(errorMap));
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ApiError<Map<String, List<String>>>> handleHandlerMethodValidationException(HandlerMethodValidationException ex) {
        Map<String, List<String>> errorMap = new HashMap<>();

        ex.getValueResults().forEach(validationResult -> {
            String field = validationResult.getMethodParameter().getParameterName();

            List<String> messages = validationResult.getResolvableErrors().stream()
                .map(MessageSourceResolvable::getDefaultMessage)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        
            if (!messages.isEmpty()) {
            errorMap.put(field, messages);
            }
        });

        return ResponseEntity.badRequest().body(buildApiError(errorMap));
    }

    @ExceptionHandler(MissingServletRequestPartException.class)
    public ResponseEntity<ApiError<String>> handleMissingPart(MissingServletRequestPartException ex) {
        return ResponseEntity.badRequest().body(buildApiError("Media file is required"));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiError<List<String>>> handleConstraintViolationException(ConstraintViolationException exception) {

        List<String> errors = new ArrayList<>();

        for (ConstraintViolation<?> violation : exception.getConstraintViolations()) {
            String errorMessage = violation.getPropertyPath() + ": " + violation.getMessage();
            errors.add(errorMessage);
        }
        return ResponseEntity.badRequest().body(buildApiError(errors));
    }

    //if any error existed but not handled by any of the above handlers
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError<String>> handleAllExceptions(Exception exception) {
        String msg = "Serverdə gözlənilməz xəta baş verdi: " + exception.getMessage();
        return ResponseEntity.badRequest().body(buildApiError(msg));
    }

    //this method is used to build ApiError
    public static <T> ApiError<T> buildApiError(T error) {

        return ApiError.<T>builder()
                .id(UUID.randomUUID().toString())
                .error(error)
                .date(new Date())
                .build();
    }

}
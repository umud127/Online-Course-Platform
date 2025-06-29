package az.the_best.onlinecourseplatform.handler;

import az.the_best.onlinecourseplatform.exception.ApiError;
import az.the_best.onlinecourseplatform.exception.BaseException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import az.the_best.onlinecourseplatform.exception.Exception;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError< Map<String, List<String>> >> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception, WebRequest request) {
        Map<String, List<String>> errorMap = new HashMap<>();

        for (ObjectError error : exception.getBindingResult().getAllErrors()) {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();

            errorMap.computeIfAbsent(fieldName, key -> new ArrayList<>()).add(errorMessage);
        }

        return ResponseEntity.badRequest().body(buildApiError(errorMap, request));
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ApiError<Map<String, List<String>>>> handleHandlerMethodValidationException(HandlerMethodValidationException ex, WebRequest request) {
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

        return ResponseEntity.badRequest().body(buildApiError(errorMap, request));
    }

    @ExceptionHandler(MissingServletRequestPartException.class)
    public ResponseEntity<ApiError<String>> handleMissingPart(MissingServletRequestPartException ex, WebRequest request) {
        return ResponseEntity.badRequest().body(buildApiError("Media file is required", request));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiError<List<String>>> handleConstraintViolationException(ConstraintViolationException exception, WebRequest request) {

        List<String> errors = new ArrayList<>();

        for (ConstraintViolation<?> violation : exception.getConstraintViolations()) {
            String errorMessage = violation.getPropertyPath() + ": " + violation.getMessage();
            errors.add(errorMessage);
        }
        return ResponseEntity.badRequest().body(buildApiError(errors, request));
    }

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ApiError<String>> handleBaseException(BaseException exception, WebRequest request) {
        return ResponseEntity.badRequest().body(buildApiError(exception.getMessage(), request));
    }

    //if any error existed but not handled by any of the above handlers
    @ExceptionHandler(java.lang.Exception.class)
    public ResponseEntity<ApiError<String>> handleAllExceptions(java.lang.Exception exception, WebRequest request) {
        String msg = "Serverdə gözlənilməz xəta baş verdi: " + exception.getMessage();
        return ResponseEntity.badRequest().body(buildApiError(msg, request));
    }

    //this method is used to build ApiError
    public <T> ApiError<T> buildApiError(T error, WebRequest webRequest) {

        return ApiError.<T>builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .exception(new Exception<>(
                        UUID.randomUUID().toString(),
                        webRequest.getDescription(false).substring(4),
                        getHostName(),
                        new Date(),
                        error))

                .build();
    }

    public String getHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            System.out.println("Host Name Not Found" + e.getMessage());
        }

        return null;
    }

}
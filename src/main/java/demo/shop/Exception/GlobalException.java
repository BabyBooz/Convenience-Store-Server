package demo.shop.Exception;

import demo.shop.DTO.Response.ApiResponse;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.validation.FieldError;


@ControllerAdvice
public class GlobalException {

  @ExceptionHandler(value = AppException.class)
  public ResponseEntity<ApiResponse<?>> handleAppException(AppException e) {
    EnumCode errorCode = e.getErrorCode();
    ApiResponse<?> apiResponse = ApiResponse.builder()
        .code(errorCode.getCode())
        .message(errorCode.getMessage())
        .build();
    return ResponseEntity.status(errorCode.getStatusCode()).body(apiResponse);
  }


  @ExceptionHandler(value = AccessDeniedException.class)
  public ResponseEntity<ApiResponse<?>> handleAccessDeniedException(AccessDeniedException e) {
    EnumCode error = EnumCode.UNAUTHORIZED;
    ApiResponse<?> apiResponse = ApiResponse.builder()
        .code(error.getStatusCode().value())
        .message("You do not have permission")
        .build();
    return ResponseEntity.status(403).body(apiResponse);
  }


  @ExceptionHandler(value = MethodArgumentNotValidException.class)
  public ResponseEntity<ApiResponse<?>> handleValidationException(MethodArgumentNotValidException e) {
    // Map field → error message
    Map<String, String> errors = new HashMap<>();
    e.getBindingResult().getFieldErrors()
        .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

    ApiResponse<?> apiResponse = ApiResponse.builder()
        .code(1001)
        .message("Validation failed")
        .result(errors) // {"email": "Email invalid", "password": "Password must be at least 6 characters"}
        .build();
    return ResponseEntity.badRequest().body(apiResponse);
  }

  @ExceptionHandler(value = RuntimeException.class)
  public ResponseEntity<ApiResponse<?>> handleRuntimeException(RuntimeException e) {
    ApiResponse<?> apiResponse = ApiResponse.builder()
        .code(1000)
        .message(e.getMessage())
        .build();
    return ResponseEntity.badRequest().body(apiResponse);
  }
}

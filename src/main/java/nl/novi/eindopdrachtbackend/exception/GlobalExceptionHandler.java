package nl.novi.eindopdrachtbackend.exception;

import nl.novi.eindopdrachtbackend.common.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
        ApiResponse apiResponse = new ApiResponse(false, ex.getMessage(), null);
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse> handleAccessDeniedException(AccessDeniedException ex) {
        ApiResponse apiResponse = new ApiResponse(false, ex.getMessage(), null);
        return new ResponseEntity<>(apiResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<ApiResponse> handleRoleNotFoundException(RoleNotFoundException ex) {
        ApiResponse apiResponse = new ApiResponse(false, ex.getMessage(), null);
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        String message = "Invalid role: " + ex.getValue();
        ApiResponse apiResponse = new ApiResponse(false, message, null);
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }
}

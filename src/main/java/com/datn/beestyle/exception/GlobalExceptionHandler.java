package com.datn.beestyle.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException e, WebRequest request) {
        ErrorResponse errorResponse = this.createErrorResponse(HttpStatus.NOT_FOUND, e.getMessage(), request);

        log.error("Path: {} - Msg error: {}",errorResponse.getPath(), errorResponse.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler({InvalidDataException.class, EmptyResultDataAccessException.class})
    public ResponseEntity<ErrorResponse> handleInvalidDataException(Exception e, WebRequest request) {
        ErrorResponse errorResponse = this.createErrorResponse(HttpStatus.CONFLICT, e.getMessage(), request);

        log.error("Path: {} - Msg error: {}",errorResponse.getPath(), errorResponse.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleInternalServerErrorException(Exception e, WebRequest request) {
        ErrorResponse errorResponse = this.createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), request);

        log.info("Class exception: {}", e.getClass());
        log.error("Path: {} - Msg error: {}",errorResponse.getPath(), errorResponse.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, ConstraintViolationException.class})
    public ResponseEntity<ErrorResponse> handleValidationException(Exception e, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ErrorResponse errorResponse = this.createErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage(), request);

        if(e instanceof MethodArgumentNotValidException) {
            BindingResult bindingResult = ((MethodArgumentNotValidException) e).getBindingResult();
            for (FieldError fieldError: bindingResult.getFieldErrors()) {
                errors.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            errorResponse.setError("Invalid Payload");
            errorResponse.setMessage(errors);
        } else if (e instanceof ConstraintViolationException) {
            Set<ConstraintViolation<?>> violations = ((ConstraintViolationException)e).getConstraintViolations();

//             "message": {
//                      "updateMaterials.requestList[3].materialName": "K de trong",
//                      "updateMaterials.requestList[1].materialName": "K de trong"
//             }
//            for (ConstraintViolation<?> violation: violations) {
//                errors.put(violation.getPropertyPath().toString(), violation.getMessage());
//            }
//            errorResponse.setMessage(errors);

//             "message": {
//                     "updateMaterials.requestList[3].materialName": "K de trong",
//                     "updateMaterials.requestList[1].materialName": "K de trong"
//             }
            List<String> errorList = violations.stream()
                    .map(err -> err.getPropertyPath() + ": " + err.getMessage()).toList();
            errorResponse.setMessage(errorList);
        }

        log.error("Path: {} - Msg error: {}",errorResponse.getPath(), errorResponse.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    private ErrorResponse createErrorResponse(HttpStatus httpStatus, String message, WebRequest request) {
        String path = request.getDescription(false).replace("uri=", "");

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setCode(httpStatus.value());
        errorResponse.setPath(path);
        errorResponse.setError(httpStatus.getReasonPhrase());
        errorResponse.setMessage(message);

        return errorResponse;
    }
}

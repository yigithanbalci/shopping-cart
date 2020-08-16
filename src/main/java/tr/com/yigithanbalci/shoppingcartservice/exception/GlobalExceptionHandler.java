package tr.com.yigithanbalci.shoppingcartservice.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

  private final ObjectMapper objectMapper = new ObjectMapper();

  @ExceptionHandler(AuthorizationException.class)
  @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
  public ResponseEntity<String> handleAuthorizationException(AuthorizationException e) {
    log.error("Authorization error: {} ", e.getLocalizedMessage(), e);
    try {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
          .body(objectMapper.writeValueAsString(e.getLocalizedMessage()));
    } catch (JsonProcessingException jsonProcessingException) {
      log.error("Json Processing error: {} ", jsonProcessingException.getLocalizedMessage(),
          jsonProcessingException);
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
  }

  @ExceptionHandler(EntityNotFoundException.class)
  @ResponseStatus(code = HttpStatus.NOT_FOUND)
  public ResponseEntity<String> handleNotFoundException(EntityNotFoundException e) {
    log.error("Not found error: {} ", e.getLocalizedMessage(), e);
    try {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body(objectMapper.writeValueAsString(e.getLocalizedMessage()));
    } catch (JsonProcessingException jsonProcessingException) {
      log.error("Json Processing error: {} ", jsonProcessingException.getLocalizedMessage(),
          jsonProcessingException);
      return ResponseEntity.notFound().build();
    }
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<String> handleValidationExceptions(
      MethodArgumentNotValidException e) {
    Map<String, String> errors = new HashMap<>();
    e.getBindingResult().getAllErrors().forEach(objectError -> {
      String fieldName = ((FieldError) objectError).getField();
      String errorMessage = objectError.getDefaultMessage();
      errors.put(fieldName, errorMessage);
    });
    log.error("Validity check exception: {} ", e.getLocalizedMessage(), e);
    try {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body(objectMapper.writeValueAsString(errors));
    } catch (JsonProcessingException jsonProcessingException) {
      log.error("Json Processing error: {} ", jsonProcessingException.getLocalizedMessage(),
          jsonProcessingException);
      return ResponseEntity.badRequest().build();
    }
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<String> handleConstraintViolationException(
      ConstraintViolationException e) {
    Map<String, String> errors = new HashMap<>();
    e.getConstraintViolations().forEach(constraintViolation -> {
      String propertyPath = constraintViolation.getPropertyPath().toString();
      String errorMessage = constraintViolation.getMessage();
      errors.put(propertyPath, errorMessage);
    });
    log.error("Validity check error: {} ", e.getLocalizedMessage(), e);
    try {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body(objectMapper.writeValueAsString(errors));
    } catch (JsonProcessingException jsonProcessingException) {
      log.error("Json Processing error: {} ", jsonProcessingException.getLocalizedMessage(),
          jsonProcessingException);
      return ResponseEntity.badRequest().build();
    }
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
  public ResponseEntity<String> handleException(Exception e) {
    log.error("Internal Server Error: {} ", e.getLocalizedMessage(), e);
    try {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(objectMapper.writeValueAsString(e.getLocalizedMessage()));
    } catch (JsonProcessingException jsonProcessingException) {
      log.error("Json Processing error: {} ", jsonProcessingException.getLocalizedMessage(),
          jsonProcessingException);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }
}
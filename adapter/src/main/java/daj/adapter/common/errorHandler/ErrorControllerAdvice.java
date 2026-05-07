package daj.adapter.common.errorHandler;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import daj.adapter.user.utils.ErrorConstrainToUserMsg;
import daj.common.error.Cause;
import daj.common.error.ErrorResponse;
import daj.common.error.ErrorResponseBody;
import jakarta.annotation.PostConstruct;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.log4j.Log4j2;

@ControllerAdvice
@Log4j2()
public class ErrorControllerAdvice extends ResponseEntityExceptionHandler {

  final private static String ERROR_MSG_INVALID_INPUT = "invalid_input";

  @PostConstruct
  void postConstruct() {
    log.info("###init### ErrorControllerAdvice ");
  }

  @ExceptionHandler(ErrorResponse.class)
  public ResponseEntity<ErrorResponseBody> handleErrorResponse(ErrorResponse ex, WebRequest request) {
    var headers = new HttpHeaders();
    var status = HttpStatus.valueOf(ex.getHttpStatus());
    return new ResponseEntity<>(ex.getBody(), headers, status);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  ResponseEntity<ErrorResponseBody> handleConstraintViolation(ConstraintViolationException e) {
    List<Cause> causes = new ArrayList<>();
    e.getConstraintViolations().forEach(cv -> causes.add(new Cause(cv.getPropertyPath().toString(), cv.getMessage())));

    final var body = new ErrorResponseBody(ERROR_MSG_INVALID_INPUT, causes);
    var headers = new HttpHeaders();
    var status = HttpStatus.valueOf(400);
    return new ResponseEntity<>(body, headers, status);
  }

  @SuppressWarnings("all")
  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<ErrorResponseBody> handleDataIntegrityViolation(DataIntegrityViolationException ex,
      WebRequest request) {
    final var userError = ErrorConstrainToUserMsg.getUserMsg(ex);

    var headers = new HttpHeaders();
    var status = HttpStatus.valueOf(userError.getHttpStatus());
    return new ResponseEntity<>(userError.getBody(), headers, status);
  }

  @Override
  @SuppressWarnings("null")
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
    final List<Cause> causes = _getCausesList(ex);
    final var body = new ErrorResponseBody(ERROR_MSG_INVALID_INPUT, causes);
    return new ResponseEntity<Object>(body, HttpStatus.BAD_REQUEST);
  }



  
  private List<Cause> _getCausesList(MethodArgumentNotValidException ex) {
    final List<Cause> causes = new ArrayList<>();
    ex.getBindingResult().getAllErrors().forEach((error) -> {
      String fieldName = ((FieldError) error).getField();
      String message = error.getDefaultMessage();
      causes.add(new Cause(fieldName, message));
    });

    Collections.sort(causes, new Comparator<Cause>() {
      public int compare(Cause o1, Cause o2) {
        // compare two instance of `Score` and return `int` as result.
        return o2.getMessage().compareTo(o1.getMessage());
      }
    });
    
    Collections.sort(causes, new Comparator<Cause>() {
      public int compare(Cause o1, Cause o2) {
        // compare two instance of `Score` and return `int` as result.
        return o2.getPath().compareTo(o1.getPath());
      }
    });

    return causes;
  }

}

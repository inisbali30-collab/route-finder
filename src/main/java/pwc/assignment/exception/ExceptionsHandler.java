package pwc.assignment.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@Slf4j
@ControllerAdvice
public class ExceptionsHandler {

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ExceptionDetails> handleAnyException(
      final Exception ex, final WebRequest request) {

    log.error("generic exception, message: {}", ex.getMessage(), ex);
    return generateErrorDetails(
        request,
        HttpStatus.INTERNAL_SERVER_ERROR,
        HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
  }

  @ExceptionHandler(value = {NoLandRouteFoundException.class})
  public ResponseEntity<ExceptionDetails> handleEntityNotFound(
      RuntimeException ex, WebRequest request) {
    log.error(ex.getMessage(), ex);
    return generateErrorDetails(request, HttpStatus.BAD_REQUEST, ex.getMessage());
  }

  private ResponseEntity<ExceptionDetails> generateErrorDetails(
      final WebRequest request, final HttpStatus httpStatus, final String error) {
    var exceptionDetails =
        new ExceptionDetails(System.currentTimeMillis(), httpStatus.value(), request.getDescription(false),
            error);
    return new ResponseEntity<>(exceptionDetails, httpStatus);
  }
}

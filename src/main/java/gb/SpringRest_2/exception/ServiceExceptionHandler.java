package gb.SpringRest_2.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ServiceExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<Object> handleNotFoundException(CustomerNotFoundException exception) {
        return ResponseEntity.notFound().build();
    }
}

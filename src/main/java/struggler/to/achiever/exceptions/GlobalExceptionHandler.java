package struggler.to.achiever.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import struggler.to.achiever.response.ErrorResponse;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Handle UserServiceException and return a custom error message
    @ExceptionHandler(UserServiceException.class)
    public ResponseEntity<Object> handleUserServiceException(UserServiceException ex) {
        // Create a custom error response
       // ErrorMessage errorMessage = new ErrorMessage(new Date(),ex.getMessage());

        ErrorResponse errorResponse = new ErrorResponse(
                new Date(),
                HttpStatus.BAD_REQUEST.value(),
                "BAD_REQUEST",
                ex.getMessage()
        );

        // Return the response as JSON
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // Handle other exceptions globally
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGlobalException(Exception ex) {
        System.out.println("Handling Global Exception: " + ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(
                new Date(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "An unexpected error occurred.",
                ex.getMessage()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Handle other exceptions globally
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(Exception ex) {
        System.out.println("Handling User Not found Exception: " + ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(
                new Date(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "No user found.", "User Not Found for given id : " +
                ex.getMessage()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}


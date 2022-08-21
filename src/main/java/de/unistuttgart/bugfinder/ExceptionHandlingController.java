package de.unistuttgart.bugfinder;

import java.util.NoSuchElementException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class ExceptionHandlingController {

  @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "no such element")
  @ExceptionHandler(NoSuchElementException.class)
  public void noSuchElement() {}

  @ExceptionHandler(IllegalArgumentException.class)
  public void handleError(HttpServletRequest request, HttpServletResponse response, IllegalArgumentException ex) {
    if (ex.getMessage().contains("Invalid UUID string: codes")) {
      response.setStatus(HttpStatus.BAD_REQUEST.value());
    }
  }
}

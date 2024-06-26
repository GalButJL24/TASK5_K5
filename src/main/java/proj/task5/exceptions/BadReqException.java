package proj.task5.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;


public class BadReqException extends ResponseStatusException {
    public BadReqException(String message){
        super(HttpStatus.BAD_REQUEST, message);
    }
}

package org.koenlars.uom.sbdemo;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * UserInputException
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserInputException extends RuntimeException    {

    public UserInputException(String message)   {
        super(message);
    }
    
}
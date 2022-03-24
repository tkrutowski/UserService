package net.focik.userservice.domain.exceptions;

public class EmailAlreadyExistsException extends ObjectAlreadyExistException {
    public EmailAlreadyExistsException(String message) {
        super(message);
    }
}

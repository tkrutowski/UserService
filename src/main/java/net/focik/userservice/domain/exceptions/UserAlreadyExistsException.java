package net.focik.userservice.domain.exceptions;

public class UserAlreadyExistsException extends ObjectAlreadyExistException {
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}

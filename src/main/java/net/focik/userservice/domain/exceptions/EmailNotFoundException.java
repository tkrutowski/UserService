package net.focik.userservice.domain.exceptions;

public class EmailNotFoundException extends ObjectNotFoundException {
    public EmailNotFoundException(String message) {
        super(message);
    }
}

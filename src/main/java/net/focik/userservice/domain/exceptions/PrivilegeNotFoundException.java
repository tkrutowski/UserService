package net.focik.userservice.domain.exceptions;

public class PrivilegeNotFoundException extends ObjectNotFoundException {
    public PrivilegeNotFoundException(String message) {
        super(message);
    }
}

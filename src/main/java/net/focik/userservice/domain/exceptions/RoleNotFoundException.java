package net.focik.userservice.domain.exceptions;

public class RoleNotFoundException extends ObjectNotFoundException {
    public RoleNotFoundException(String message) {
        super(message);
    }
}

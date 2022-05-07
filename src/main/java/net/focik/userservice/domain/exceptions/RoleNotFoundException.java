package net.focik.userservice.domain.exceptions;

import java.util.function.Supplier;

public class RoleNotFoundException extends ObjectNotFoundException {
    public RoleNotFoundException(String message) {
        super(message);
    }
}

package io.github.arkanjoms.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR, reason = "invalid type, only csv is permitted")
public class InvalidCSVException extends RuntimeException {
    private static final long serialVersionUID = 4456381541521572495L;
}

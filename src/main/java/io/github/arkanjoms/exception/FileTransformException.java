package io.github.arkanjoms.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR, reason = "error when parsing file")
public class FileTransformException extends RuntimeException {
    public FileTransformException(Throwable cause) {
        super(cause);
    }
}

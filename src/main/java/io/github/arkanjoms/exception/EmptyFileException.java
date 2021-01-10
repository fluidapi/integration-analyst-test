package io.github.arkanjoms.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR, reason = "file not provided")
public class EmptyFileException extends RuntimeException {
}

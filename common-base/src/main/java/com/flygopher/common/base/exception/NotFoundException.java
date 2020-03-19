package com.flygopher.common.base.exception;

import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.function.Supplier;

public class NotFoundException extends AppException {

    public NotFoundException(IExceptionCode code, String message) {
        super(code, message);
    }

    public NotFoundException(IExceptionCode code, String message, List<AppError> details) {
        super(code, message, details);
    }

    public static Supplier<NotFoundException> notFoundException(String... messages) {
        String message = StringUtils.join(messages, " ");
        return () -> new NotFoundException(ExceptionCode.NOT_FOUND, message);
    }
}

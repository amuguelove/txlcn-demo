package com.flygopher.common.base.errormapping;

import com.flygopher.common.base.exception.AppException;
import com.flygopher.common.base.exception.ApplicationError;
import com.flygopher.common.base.exception.IExceptionCode;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.Optional;

public class AbstractApplicationErrorMapping {

    public AbstractApplicationErrorMapping() {
    }

    protected ApplicationError applicationError(final IExceptionCode code, String message, String path) {
        return ApplicationError.builder().code(code.getValue()).message(message).path(path).build();
    }

    protected ApplicationError applicationError(
            IExceptionCode code, String message, List<AppException.AppError> errors, String path) {
        return ApplicationError.builder()
                .code(code.getValue())
                .message(message)
                .details(errors)
                .path(path)
                .build();
    }

    protected ApplicationError applicationError(AppException ex, String path) {
        return ApplicationError.builder()
                .code(ex.getCode().getValue())
                .message(ex.getMessage())
                .details(ex.getDetails())
                .path(path)
                .build();
    }

    protected AppException.AppError errorDetail(FieldError fieldError) {
        String message =
                Optional.ofNullable(fieldError)
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .orElse("");
        String field = Optional.ofNullable(fieldError).map(FieldError::getField).orElse("");
        return AppException.AppError.builder().field(field).message(message).build();
    }
}

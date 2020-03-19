package com.flygopher.common.base.exception;

import lombok.Getter;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Getter
public class ParamConflictException extends AppException {

    public ParamConflictException(IExceptionCode code, String message) {
        super(code, message);
    }

    public ParamConflictException(IExceptionCode code, String message, List<AppError> details) {
        super(code, message, details);
    }

    public static Supplier<ParamConflictException> duplicateConflictException(List<String> fields) {
        List<AppError> errors =
                fields.stream()
                        .map(field -> AppError.builder()
                                .field(field)
                                .message(field + " should not be duplicated")
                                .build())
                        .collect(Collectors.toList());

        return () -> new ParamConflictException(ExceptionCode.DUPLICATE_CONFLICT, "请求参数重复", errors);
    }

    public static Supplier<ParamConflictException> uneditableConflictException(List<String> fields) {
        List<AppError> errors =
                fields.stream()
                        .map(field -> AppError.builder()
                                .field(field)
                                .message(field + " should not be edited")
                                .build())
                        .collect(Collectors.toList());

        return () -> new ParamConflictException(ExceptionCode.UNEDITABLE_CONFLICT, "字段不可编辑", errors);
    }
}

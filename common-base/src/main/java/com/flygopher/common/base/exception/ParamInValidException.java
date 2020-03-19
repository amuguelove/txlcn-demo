package com.flygopher.common.base.exception;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class ParamInValidException extends AppException {

    public ParamInValidException(IExceptionCode code, String message) {
        super(code, message);
    }

    public ParamInValidException(IExceptionCode code, String message, List<AppError> details) {
        super(code, message, details);
    }

    public static Supplier<ParamInValidException> paramInValidException(String message) {
        return () -> new ParamInValidException(ExceptionCode.VALIDATION_FAILED, message);
    }

    public static Supplier<ParamInValidException> paramInValidException(List<String> fields) {
        List<AppError> errors =
                fields.stream()
                        .map(field -> AppError.builder()
                                .field(field)
                                .message(field + " should be valid")
                                .build())
                        .collect(Collectors.toList());

        return () -> new ParamInValidException(ExceptionCode.VALIDATION_FAILED, "请求参数不合法", errors);
    }
}

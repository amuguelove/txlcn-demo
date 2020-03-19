package com.flygopher.common.base.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public abstract class AppException extends RuntimeException {

    private final IExceptionCode code;

    private final String message;

    private List<AppError> details = new ArrayList<>();

    public AppException(IExceptionCode code, String message) {
        this.code = code;
        this.message = message;
    }

    public AppException(IExceptionCode code, String message, List<AppError> details) {
        this.code = code;
        this.message = message;
        this.details = details;
    }

    @Builder
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AppError {
        private String field;
        private String message;
        private Object value;
    }
}

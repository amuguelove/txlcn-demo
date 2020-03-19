package com.flygopher.common.base.exception;

public enum ExceptionCode implements IExceptionCode {

    INTERNAL_SERVER_ERROR,
    ACCESS_DENIED,
    VALIDATION_FAILED,
    REQUEST_NOT_READABLE,
    REQUEST_PARAMETER_MISMATCHED,
    NOT_FOUND,
    DUPLICATE_CONFLICT,
    MAXIMUM_UPLOAD_SIZE_EXCEEDED,
    UPLOAD_FILE_FAILED,
    LOCK_OCCUPIED,
    UNEDITABLE_CONFLICT;

    @Override
    public String getValue() {
        return this.name();
    }
}

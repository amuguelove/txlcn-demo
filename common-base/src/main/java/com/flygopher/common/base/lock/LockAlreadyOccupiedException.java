package com.flygopher.common.base.lock;

import com.flygopher.common.base.exception.AppException;
import com.flygopher.common.base.exception.ExceptionCode;
import com.flygopher.common.base.exception.IExceptionCode;

import java.util.function.Supplier;

public class LockAlreadyOccupiedException extends AppException {

    public LockAlreadyOccupiedException(IExceptionCode code, String message) {
        super(code, message);
    }

    public static Supplier<LockAlreadyOccupiedException> lockAlreadyOccupiedException(String key) {
        return () -> new LockAlreadyOccupiedException(ExceptionCode.LOCK_OCCUPIED, key + "已被锁定");
    }

}

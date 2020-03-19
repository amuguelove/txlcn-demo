package com.flygopher.common.base.errormapping;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flygopher.common.base.exception.AppException;
import com.flygopher.common.base.exception.ApplicationError;
import com.flygopher.common.base.exception.ExceptionCode;
import com.flygopher.common.base.exception.NotFoundException;
import com.flygopher.common.base.exception.ParamConflictException;
import com.flygopher.common.base.exception.ParamInValidException;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@ControllerAdvice
@ResponseBody
public class ApplicationErrorMapping extends AbstractApplicationErrorMapping {

    @Autowired
    private ObjectMapper objectMapper;

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ApplicationError mapUnhandledException(Throwable ex, HttpServletRequest request) {
        log.error("unhandled exception", ex);
        return applicationError(ExceptionCode.INTERNAL_SERVER_ERROR, ex.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler(AppException.class)
    @ResponseStatus(CONFLICT)
    public ApplicationError mapAppException(AppException ex, HttpServletRequest request) {
        log.error("app exception", ex);
        return applicationError(ex, request.getRequestURI());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(BAD_REQUEST)
    public ApplicationError mapArgumentNotValidException(
            MethodArgumentNotValidException ex, HttpServletRequest request) {
        log.error("para is not valid", ex);
        List<AppException.AppError> errors =
                ex.getBindingResult().getFieldErrors().stream()
                        .map(this::errorDetail)
                        .collect(toList());
        return applicationError(ExceptionCode.VALIDATION_FAILED, "para is not valid", errors, request.getRequestURI());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(BAD_REQUEST)
    public ApplicationError mapConstraintViolationException(
            ConstraintViolationException ex, HttpServletRequest request) {
        log.error("para is not valid", ex);
        List<AppException.AppError> errors =
                ex.getConstraintViolations().stream()
                        .map(constraintViolation -> {
                            List<AppException.AppError> list = new ArrayList<>();
                            constraintViolation.getPropertyPath().forEach(node -> {
                                if (node instanceof Path.ParameterNode) {
                                    AppException.AppError appError = new AppException.AppError();
                                    appError.setMessage(constraintViolation.getMessage());
                                    appError.setField(node.getName());
                                    appError.setValue(constraintViolation.getInvalidValue());
                                    list.add(appError);
                                }
                            });
                            return list;
                        })
                        .flatMap(Collection::stream)
                        .collect(toList());
        return applicationError(ExceptionCode.VALIDATION_FAILED, "para is not valid", errors, request.getRequestURI());
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(BAD_REQUEST)
    public ApplicationError mapBindException(BindException ex, HttpServletRequest request) {
        log.error("bind argument failed", ex);
        List<AppException.AppError> errors =
                ex.getBindingResult().getFieldErrors().stream()
                        .map(this::errorDetail)
                        .collect(toList());
        return applicationError(ExceptionCode.VALIDATION_FAILED, ex.getMessage(), errors, request.getRequestURI());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(BAD_REQUEST)
    public ApplicationError mapMissingServletRequestParameterException(
            MissingServletRequestParameterException ex, HttpServletRequest request) {
        log.error("missing parameter exception", ex);
        return applicationError(ExceptionCode.VALIDATION_FAILED, ex.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(BAD_REQUEST)
    public ApplicationError notReadableException(HttpMessageNotReadableException ex, HttpServletRequest request) {
        log.error("http request not readable", ex);
        return applicationError(ExceptionCode.REQUEST_NOT_READABLE, ex.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(BAD_REQUEST)
    public ApplicationError badArgumentTypeException(MethodArgumentTypeMismatchException ex,
                                                     HttpServletRequest request) {
        log.error("request parameter mismatched", ex);
        return applicationError(
                ExceptionCode.REQUEST_PARAMETER_MISMATCHED, ex.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public ApplicationError mapNotFoundException(NotFoundException ex, HttpServletRequest request) {
        log.error("data not found", ex);
        return applicationError(ex, request.getRequestURI());
    }

    @ExceptionHandler(ParamConflictException.class)
    @ResponseStatus(CONFLICT)
    public ApplicationError mapParamConflictException(ParamConflictException ex, HttpServletRequest request) {
        log.error("param conflict", ex);
        return applicationError(ex, request.getRequestURI());
    }

    @ExceptionHandler(FeignException.class)
    @ResponseBody
    public ResponseEntity mapFeignException(FeignException ex, HttpServletRequest request) throws IOException {
        log.error("feign exception", ex);
        return ResponseEntity.status(ex.status())
                .body(objectMapper.readValue(ex.content(), Object.class));
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    @ResponseStatus(BAD_REQUEST)
    public ApplicationError mapMaxUploadSizeExceededException(MaxUploadSizeExceededException ex,
                                                              HttpServletRequest request) {
        log.error("max upload size exceeded exception", ex);
        return applicationError(ExceptionCode.MAXIMUM_UPLOAD_SIZE_EXCEEDED,
                "超过文件大小上限", request.getRequestURI());
    }

    @ExceptionHandler(MultipartException.class)
    @ResponseStatus(BAD_REQUEST)
    public ApplicationError mapMultipartException(MultipartException ex, HttpServletRequest request) {
        log.error("upload file failed", ex);
        return applicationError(ExceptionCode.UPLOAD_FILE_FAILED,
                "文件上传失败，请重试", request.getRequestURI());
    }

    @ExceptionHandler(ParamInValidException.class)
    @ResponseStatus(BAD_REQUEST)
    public ApplicationError mapParamInvalidException(ParamInValidException ex, HttpServletRequest request) {
        log.error("param invalid, exception: ", ex);
        return applicationError(ex, request.getRequestURI());
    }
}

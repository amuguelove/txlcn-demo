package com.flygopher.common.base.exception;

import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
public class ApplicationError {

    private String code;
    private String message;
    private String path;
    @Default
    private Instant timestamp = Instant.now();
    @Default
    private List<AppException.AppError> details = new ArrayList<>();
}

package com.flygopher.common.base.feign;

import feign.Logger;
import feign.Request;

public class FeignLogger extends Logger {

    private final org.slf4j.Logger logger;

    public FeignLogger(org.slf4j.Logger logger) {
        this.logger = logger;
    }

    @Override
    protected void log(String configKey, String format, Object... args) {
        logger.info(String.format(methodTag(configKey) + format, args));
    }

    @Override
    protected void logRequest(String configKey, Level logLevel, Request request) {
        if (logger.isDebugEnabled()) {
            super.logRequest(configKey, logLevel, request);
        }
    }
}

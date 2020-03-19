package com.flygopher.common.base.logging;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Aspect
@Component
@Slf4j
public class WebLogAspect {

    @Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping) "
            + " || @annotation(org.springframework.web.bind.annotation.PutMapping) "
            + " || @annotation(org.springframework.web.bind.annotation.PatchMapping) "
            + " || @annotation(org.springframework.web.bind.annotation.DeleteMapping)")
    public void webLog() {
    }

    @Around("webLog()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        String methodName = joinPoint.getSignature().getName();

        log.info("开始请求，请求地址: {}, 请求方法: {}, 方法名: {}",
                getHttpRequestUri(), getHttpRequestMethod(), methodName);

        // do business logic
        Object result = joinPoint.proceed();

        log.info("结束请求，请求地址: {}, 请求方法: {}, 方法名: {}, costs: {} ms",
                getHttpRequestUri(), getHttpRequestMethod(), methodName, (System.currentTimeMillis() - startTime));

        return result;
    }

    private HttpServletRequest getHttpRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes) {
            return ((ServletRequestAttributes) requestAttributes).getRequest();
        }
        return null;
    }

    private String getHttpRequestUri() {
        return Optional.ofNullable(getHttpRequest()).map(HttpServletRequest::getRequestURI).orElse(Strings.EMPTY);
    }

    private String getHttpRequestMethod() {
        return Optional.ofNullable(getHttpRequest()).map(HttpServletRequest::getMethod).orElse(Strings.EMPTY);
    }

}

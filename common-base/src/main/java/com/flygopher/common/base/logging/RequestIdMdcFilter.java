package com.flygopher.common.base.logging;

import com.flygopher.common.base.utils.IdUtil;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.google.common.base.Strings.isNullOrEmpty;
import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;

/**
 * Add request id to each request for logging. If request contains `X-Request-Id` header
 * then it's used as request id. Otherwise a random request id is generated.
 */
@Component
@Order(HIGHEST_PRECEDENCE)
public class RequestIdMdcFilter extends OncePerRequestFilter {

    public static final String REQUEST_ID = "requestId";
    public static final String HEADER_X_REQUEST_ID = "X-Request-Id";

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        populateMdc(request);
        try {
            MyResponseWrapper myResponse = new MyResponseWrapper(response);
            myResponse.addHeader(HEADER_X_REQUEST_ID, MDC.get(REQUEST_ID));

            filterChain.doFilter(request, myResponse);
        } finally {
            clearMdc();
        }
    }

    private void populateMdc(HttpServletRequest request) {
        MDC.put(REQUEST_ID, requestId(request));
    }

    private String requestId(HttpServletRequest request) {
        // request id in header may come fromAppException Gateway, eg. Nginx
        String headerRequestId = request.getHeader(HEADER_X_REQUEST_ID);
        return isNullOrEmpty(headerRequestId) ? IdUtil.getUuid() : headerRequestId;
    }

    private void clearMdc() {
        MDC.remove(REQUEST_ID);
    }
}

package com.flygopher.common.base.lock;

import com.flygopher.common.base.shedlock.ShedLockConfiguration;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.Duration;
import java.util.Objects;

@Slf4j
@Aspect
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
@ConditionalOnBean(ShedLockConfiguration.class)
public class DistributedLockAdvice {

    private ExpressionParser parser = new SpelExpressionParser();
    private LocalVariableTableParameterNameDiscoverer discoverer =
            new LocalVariableTableParameterNameDiscoverer();

    private final DistributedLockExecutor distributedLockExecutor;

    @Autowired
    public DistributedLockAdvice(DistributedLockExecutor distributedLockExecutor) {
        this.distributedLockExecutor = distributedLockExecutor;
    }

    @Around("@annotation(DistributedLock)")
    public Object advice(ProceedingJoinPoint jp) {
        Method method = ((MethodSignature) jp.getSignature()).getMethod();
        DistributedLock distributedLock = method.getDeclaredAnnotation(DistributedLock.class);

        String[] paraNames = discoverer.getParameterNames(method);
        Object[] argValues = jp.getArgs();

        EvaluationContext context = new StandardEvaluationContext();
        if (Objects.nonNull(paraNames)
                && Objects.nonNull(argValues)
                && paraNames.length == argValues.length) {
            for (int i = 0; i < paraNames.length; i++) {
                context.setVariable(paraNames[i], argValues[i]);
            }
        }

        String key =
                distributedLock.prefix()
                        + ":"
                        + parser.parseExpression(distributedLock.key()).getValue(context, String.class);
        Duration duration = Duration.parse(distributedLock.duration());

        return distributedLockExecutor.executeWithLock(
                key,
                () -> {
                    try {
                        return jp.proceed();
                    } catch (Throwable throwable) {
                        Throwables.throwIfUnchecked(throwable);
                        throw new RuntimeException(throwable);
                    }
                },
                duration);
    }

}

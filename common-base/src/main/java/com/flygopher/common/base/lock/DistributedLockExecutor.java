package com.flygopher.common.base.lock;

import com.flygopher.common.base.shedlock.ShedLockConfiguration;
import net.javacrumbs.shedlock.core.LockConfiguration;
import net.javacrumbs.shedlock.core.LockProvider;
import net.javacrumbs.shedlock.core.SimpleLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.function.Supplier;

import static com.flygopher.common.base.lock.LockAlreadyOccupiedException.lockAlreadyOccupiedException;

@Component
@ConditionalOnBean(ShedLockConfiguration.class)
public class DistributedLockExecutor {

    private final LockProvider lockProvider;

    @Autowired
    public DistributedLockExecutor(LockProvider lockProvider) {
        this.lockProvider = lockProvider;
    }

    public <T> T executeWithLock(String key, Supplier<T> supplier, Duration maxRunTime) {
        LockConfiguration lockConfiguration = new LockConfiguration(key, Instant.now().plus(maxRunTime));

        Optional<SimpleLock> lock = lockProvider.lock(lockConfiguration);
        if (!lock.isPresent()) {
            throw lockAlreadyOccupiedException(key).get();
        }

        try {
            return supplier.get();
        } finally {
            lock.get().unlock();
        }
    }

    public void executeWithLock(String key, Runnable runnable, Duration maxRunTime) {
        LockConfiguration lockConfiguration = new LockConfiguration(key, Instant.now().plus(maxRunTime));

        Optional<SimpleLock> lock = lockProvider.lock(lockConfiguration);
        if (!lock.isPresent()) {
            throw lockAlreadyOccupiedException(key).get();
        }

        try {
            runnable.run();
        } finally {
            lock.get().unlock();
        }
    }

}

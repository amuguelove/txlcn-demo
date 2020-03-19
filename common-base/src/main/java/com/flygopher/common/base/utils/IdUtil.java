package com.flygopher.common.base.utils;

import java.util.UUID;

public class IdUtil {

    public static String getUuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}

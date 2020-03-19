package com.flygopher.common.base.utils;

import java.util.concurrent.atomic.AtomicInteger;

public class IDGenerator {

    private static final String IP;

    private static final String HEX_IP;
    private static final String TIMESTAMP_PLACEHOLDER = "00000000000";
    private static final int COUNT_MODEL = 90000;
    private static final int MIN_COUNT = 10000;
    private static final int IP_BYTE_LENGTH = 4;
    private static final int HEX_CHAR_BIT_LENGTH = 4;
    private static final int LOW_BIT_FILLED = 0x0F;
    private static final AtomicInteger COUNTER = new AtomicInteger(0);

    static {
        IP = IPUtils.getVMIp();
        HEX_IP = ipToHex(IP);
    }

    private static String timestampToHex(long timestamp) {
        String hexVal = Long.toHexString(timestamp);
        StringBuffer paddingVal = new StringBuffer(TIMESTAMP_PLACEHOLDER);
        paddingVal.replace(
                TIMESTAMP_PLACEHOLDER.length() - hexVal.length(),
                TIMESTAMP_PLACEHOLDER.length(),
                hexVal);
        return paddingVal.toString();
    }

    private static String getCount() {
        return String.valueOf(
                (COUNTER.getAndIncrement() & Integer.MAX_VALUE) % COUNT_MODEL + MIN_COUNT);
    }

    private static String ipToHex(String ip) {
        byte[] ipData = new byte[IP_BYTE_LENGTH];
        String[] ipStrArray = ip.split("\\.");
        for (int i = 0; i < IP_BYTE_LENGTH; i++) {
            ipData[i] = (byte) (Integer.parseInt(ipStrArray[i]));
        }
        StringBuilder hexStr = new StringBuilder(IP_BYTE_LENGTH * 2);
        for (int i = 0; i < IP_BYTE_LENGTH; i++) {
            hexStr.append(Integer.toHexString(ipData[i] >> HEX_CHAR_BIT_LENGTH & LOW_BIT_FILLED));
            hexStr.append(Integer.toHexString(ipData[i] & LOW_BIT_FILLED));
        }
        return hexStr.toString();
    }

    public static String newId() {
        return timestampToHex(System.currentTimeMillis()) + HEX_IP + getCount();
    }
}

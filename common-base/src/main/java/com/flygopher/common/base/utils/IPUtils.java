package com.flygopher.common.base.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.net.InetAddress;

@Slf4j
public class IPUtils {

    private static final String JVM_SERVER_IP = "server.ip";

    private static String vmIp;

    public static String getVMIp() {
        if (StringUtils.isEmpty(vmIp)) {
            try {
                String vmIp = System.getProperty(JVM_SERVER_IP);
                if (!StringUtils.isEmpty(vmIp)) {
                    IPUtils.vmIp = vmIp;
                    return IPUtils.vmIp;
                }

                if (StringUtils.isEmpty(IPUtils.vmIp)) {
                    IPUtils.vmIp = InetAddress.getLocalHost().getHostAddress();
                }
            } catch (Exception e) {
                log.warn("get ip failed", e);
                vmIp = "127.0.0.1";
            }
        }
        return vmIp;
    }
}

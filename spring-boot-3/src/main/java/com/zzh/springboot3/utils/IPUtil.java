package com.zzh.springboot3.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.ServletServerHttpRequest;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/10/31 21:14
 */
@Slf4j
public class IPUtil {


    public static String getIpAddress(ServletServerHttpRequest servletServerHttpRequest) {

        HttpHeaders headers = servletServerHttpRequest.getHeaders();

        String ipAddress = headers.getFirst("X-Forward-For");

        if (StringUtils.isEmpty(ipAddress) || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = headers.getFirst("Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(ipAddress) || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = headers.getFirst("WL-Proxy-Client-IP");
        }

        if (StringUtils.isEmpty(ipAddress) || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = servletServerHttpRequest.getRemoteAddress().getAddress().getHostAddress();
            if (ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")) {
                try {
                    ipAddress = InetAddress.getLocalHost().getHostAddress();
                } catch (UnknownHostException e) {
                    log.error("获取ip地址失败", e);
                }
            }
        }
        if (ipAddress != null && ipAddress.indexOf(",") > 0) {
            ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
        }
        log.info("获取的IP地址：{}",ipAddress);
        return ipAddress;
    }


}

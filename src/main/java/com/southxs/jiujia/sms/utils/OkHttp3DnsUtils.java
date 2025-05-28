package com.southxs.jiujia.sms.utils;

import okhttp3.Dns;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * OkHttp3Dns.java
 *
 * @author Liuxipu
 * @date 2021/9/24
 */
public class OkHttp3DnsUtils implements Dns {
    private static final Logger logger = LoggerFactory.getLogger(OkHttp3DnsUtils.class);

    private final long timeout;

    private final TimeUnit unit;

    public OkHttp3DnsUtils(long timeout, TimeUnit unit) {
        this.timeout = timeout;
        this.unit = unit;
    }


    @Override
    public List<InetAddress> lookup(String hostname) throws UnknownHostException {
        if (null == hostname) {
            logger.error("OkHttp3 DNS 解析失败:{}", "hostname为空");
            throw new UnknownHostException("hostname == null");
        } else {
            try {
                FutureTask<List<InetAddress>> task = new FutureTask<>(
                        new Callable<List<InetAddress>>() {
                            @Override
                            public List<InetAddress> call() throws Exception {
                                return Arrays.asList(InetAddress.getAllByName(hostname));
                            }
                        });
                new Thread(task).start();
                return task.get(timeout, unit);
            } catch (Exception var4) {
                logger.error("OkHttp3 DNS 解析失败: Broken system behaviour for dns lookup of {}", hostname);
                UnknownHostException unknownHostException =
                        new UnknownHostException("Broken system behaviour for dns lookup of " + hostname);
                unknownHostException.initCause(var4);
                throw unknownHostException;
            }
        }
    }
}
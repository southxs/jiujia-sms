package com.southxs.jiujia.sms.utils;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.X509TrustManager;
import java.util.concurrent.TimeUnit;

/**
 * JiuJiaSmsHttpClient.java
 *
 * @author Mr.vivion
 * @date 2025/5/6
 */
public class JiuJiaSmsHttpClient {

    private static final Logger logger = LoggerFactory.getLogger(JiuJiaSmsHttpClient.class);

    private static final int DEFAULT_CONNECTION_TIMEOUT = 60;

    private static final int DEFAULT_SO_TIMEOUT = 120;

    private static final int DEFAULT_WRITE_TIMEOUT = 60;

    private static OkHttpClient jiuJiaSmsHttpClient = null;

    public static synchronized OkHttpClient getJiuJiaSmsHttpClient() {
        if (null == jiuJiaSmsHttpClient) {
            X509TrustManager manager = SSLSocketClientUtils.getX509TrustManager();
            jiuJiaSmsHttpClient = new OkHttpClient.Builder()
                    .retryOnConnectionFailure(false)//取消重试
                    .dns(new OkHttp3DnsUtils(DEFAULT_CONNECTION_TIMEOUT, TimeUnit.SECONDS))
                    .connectTimeout(DEFAULT_CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(DEFAULT_SO_TIMEOUT, TimeUnit.SECONDS)
                    .writeTimeout(DEFAULT_WRITE_TIMEOUT, TimeUnit.SECONDS)
                    .sslSocketFactory(SSLSocketClientUtils.getSocketFactory(manager), manager)
                    .hostnameVerifier(SSLSocketClientUtils.getHostnameVerifier())
                    .connectionPool(new ConnectionPool(50, 30, TimeUnit.SECONDS))
                    .build();
        }
        return jiuJiaSmsHttpClient;
    }

}

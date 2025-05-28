package com.southxs.jiujia.sms;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.southxs.jiujia.sms.bean.*;
import com.southxs.jiujia.sms.utils.CryptoUtils;
import com.southxs.jiujia.sms.utils.JiuJiaSmsHttpClient;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * JiuJiaSmsHandler.java
 *
 * @author Mr.vivion
 * @date 2025/5/6
 */
public class JiuJiaSmsHandler {

    private static final Logger logger = LoggerFactory.getLogger(JiuJiaSmsHandler.class);
    private static final String SMS_SEND = "http://eh.jj-mob.com:28000/v2/sms/send";
    private static final String SMS_PULL = "http://eh.jj-mob.com:28000/v2/sms/mo/pull";

    /**
     * 发送短信
     *
     * @param request
     * @return
     */
    public static SendSmsResponse sendSms(SendSmsRequest request) {
        Map<String, String> params = new HashMap<>();
        params.put("account", request.getAccount());
        params.put("token", CryptoUtils.sha1Hex(request.getAccount(), request.getPassword(), request.getTimestamp()));
        params.put("ts", request.getTimestamp());
        params.put("dest", request.getDest());
        params.put("content", CryptoUtils.urlEncode(request.getContent()));
        return unifiedRequest(SMS_SEND, params);
    }

    /**
     * 发送短信
     *
     * @param request
     * @return
     */
    public static boolean sendSmsBoolean(SendSmsRequest request) {
        Map<String, String> params = new HashMap<>();
        params.put("account", request.getAccount());
        params.put("token", CryptoUtils.sha1Hex(request.getAccount(), request.getPassword(), request.getTimestamp()));
        params.put("ts", request.getTimestamp());
        params.put("dest", request.getDest());
        params.put("content", CryptoUtils.urlEncode(request.getContent()));
        SendSmsResponse response = unifiedRequest(SMS_SEND, params);
        return null != response && response.isSuccess();
    }

    /**
     * 批量发送短信
     *
     * @param request
     */
    public static boolean sendBatchSmsBoolean(BatchSendSmsRequest request) {
        Map<String, String> params = new HashMap<>();
        params.put("account", request.getAccount());
        params.put("token", CryptoUtils.sha1Hex(request.getAccount(), request.getPassword(), request.getTimestamp()));
        params.put("ts", request.getTimestamp());
        params.put("dest", String.join(",", request.getReceiveNumbers()));
        params.put("content", CryptoUtils.urlEncode(request.getReceiveContents()));
        SendSmsResponse response = unifiedRequest(SMS_SEND, params);
        return null != response && response.isSuccess();
    }

    /**
     * 批量发送短信
     *
     * @param request
     */
    public static SendSmsResponse sendBatchSms(BatchSendSmsRequest request) {
        Map<String, String> params = new HashMap<>();
        params.put("account", request.getAccount());
        params.put("token", CryptoUtils.sha1Hex(request.getAccount(), request.getPassword(), request.getTimestamp()));
        params.put("ts", request.getTimestamp());
        params.put("dest", String.join(",", request.getReceiveNumbers()));
        params.put("content", CryptoUtils.urlEncode(request.getReceiveContents()));
        return unifiedRequest(SMS_SEND, params);
    }

    /**
     * 获取上行短信
     *
     * @param request
     * @return
     */
    public static PullUpstreamSmsResponse pullUpstreamSms(PullUpstreamSmsRequest request) {
        Map<String, String> params = new HashMap<>();
        params.put("account", request.getAccount());
        params.put("token", CryptoUtils.sha1Hex(request.getAccount(), request.getPassword(), request.getTimestamp()));
        params.put("ts", request.getTimestamp());
        SendSmsResponse response = unifiedRequest(SMS_PULL, params);
        if (null != response && response.isSuccess() && null != response.getData()) {
            return JSONObject.parseObject(response.getData(), PullUpstreamSmsResponse.class);
        }
        return null;
    }

    /**
     * 统一请求
     *
     * @param url
     * @param params
     * @return
     */
    public static SendSmsResponse unifiedRequest(String url, Map<String, String> params) {
        logger.info("【北京久佳 : SMS】请求地址 : {}", url);
        FormBody.Builder builder = new FormBody.Builder();
        params.forEach(builder::add);
        RequestBody body = builder.build();
        Request request = new Request.Builder().url(url).post(body).build();
        logger.info("【北京久佳 : SMS】请求参数 : {}", CryptoUtils.urlDecoder(params));
        try (Response response = JiuJiaSmsHttpClient.getJiuJiaSmsHttpClient().newCall(request).execute()) {
            if (!response.isSuccessful()) {
                String err = String.format("HTTP请求失败，code=%d, message=%s", response.code(), response.message());
                logger.error("【北京久佳 : SMS】请求失败 : {}", err);
                return null;
            }
            String respBody = response.body() != null ? response.body().string() : null;
            logger.info("【北京久佳 : SMS】请求结果 : {}", respBody);
            if (null == respBody) {
                return null;
            }
            SendSmsResponse smsResp = JSON.parseObject(respBody, SendSmsResponse.class);
            if (!smsResp.isSuccess()) {
                logger.error("【北京久佳 : SMS】发送失败 : {}", smsResp.getErrorMessage());
                return null;
            }
            return smsResp;
        } catch (IOException ex) {
            logger.error("【北京久佳 : SMS】请求异常 : {}", ex.getMessage(), ex);
            return null;
        }
    }

}

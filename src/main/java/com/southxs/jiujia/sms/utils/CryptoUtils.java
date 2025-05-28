package com.southxs.jiujia.sms.utils;

import com.alibaba.fastjson.JSON;
import com.southxs.jiujia.sms.bean.SmsBean;
import net.sf.cglib.beans.BeanMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * CryptoUtil.java
 *
 * @author Mr.vivion
 * @date 2025/5/6
 */
public class CryptoUtils {

    private static final Logger logger = LoggerFactory.getLogger(CryptoUtils.class);

    private static final String DELIMITER = " ||| ";
    private static final String ENCODING = "UTF-8";

    /**
     * URL 编码
     *
     * @param content
     * @return
     */
    public static String urlEncode(String content) {
        try {
            // 使用 UTF-8 进行编码，推荐方式 :contentReference[oaicite:2]{index=2}
            return URLEncoder.encode(content, ENCODING);
        } catch (UnsupportedEncodingException e) {
            logger.error("URL 编码失败", e);
            return null;
        }
    }

    /**
     * URL 解码
     *
     * @param params
     * @return
     */
    public static String urlDecoder(Map<String, String> params) {
        return urlDecoder(JSON.toJSONString(params));
    }

    public static String urlDecoder(String decoderParams) {
        try {
            // 使用 UTF-8 进行解码
            return URLDecoder.decode(decoderParams, ENCODING);
        } catch (UnsupportedEncodingException e) {
            logger.error("URL 编码失败", e);
            return null;
        }
    }

    /**
     * URL 编码
     *
     * @param receiveContents
     * @return
     */
    public static String urlEncode(List<String> receiveContents) {
        if (receiveContents == null || receiveContents.isEmpty()) {
            return "";
        }
        try {
            // 使用 StringJoiner 或者 StringBuilder 拼接列表元素，中间以 "||| " 分隔 :contentReference[oaicite:0]{index=0}
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < receiveContents.size(); i++) {
                sb.append(URLEncoder.encode(receiveContents.get(i), ENCODING));
                if (i < receiveContents.size() - 1) {
                    sb.append(DELIMITER);
                }
            }
            return sb.toString();
        } catch (UnsupportedEncodingException e) {
            logger.error("URL 编码失败", e);
            return "";
        }
    }


    /**
     * SHA1加密 : 访问令牌 ; 算法为 : sha1(account + password + timestamp)
     *
     * @param bean
     * @return
     */
    public static String sha1Hex(SmsBean bean) {
        return sha1Hex(bean.getAccount() + bean.getPassword() + bean.getTimestamp());
    }

    /**
     * SHA1加密 : 访问令牌 ; 算法为 : sha1(account + password + timestamp)
     *
     * @param account
     * @param password
     * @param timestamp
     * @return
     */
    public static String sha1Hex(String account, String password, String timestamp) {
        return sha1Hex(account + password + timestamp);
    }


    /**
     * SHA1加密 : 访问令牌 ; 算法为 : sha1(account + password + timestamp)
     *
     * @param input
     * @return
     * @throws Exception
     */

    public static String sha1Hex(String input) {
        if (null == input) {
            return null;
        }
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] digest = md.digest(input.getBytes(StandardCharsets.UTF_8));
            return DatatypeConverter.printHexBinary(digest).toLowerCase();
        } catch (NoSuchAlgorithmException ex) {
            logger.error("SHA1加密失败 :{}", ex.getMessage(), ex);
        }
        return null;
    }

    /**
     * Bean 转 Map
     *
     * @param bean
     * @param <T>
     * @return
     */
    public static <T> SortedMap<String, String> beanToMap(T bean) {
        SortedMap<String, String> map = new TreeMap<>();
        if (bean != null) {
            BeanMap beanMap = BeanMap.create(bean);
            for (Object key : beanMap.keySet()) {
                if (null != beanMap.get(key)) {
                    map.put(String.valueOf(key), beanMap.get(key).toString());
                }
            }
        }
        return map;
    }
}

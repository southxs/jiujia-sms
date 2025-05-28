package com.southxs.jiujia.sms.bean;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * SmsSendResponse.java
 * 响应体，用于接收短信接口返回结果
 *
 * @author Mr.vivion
 * @date 2025/5/6
 */
public class SendSmsResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private static final Map<String, String> ERROR_MESSAGE_MAP;

    static {
        Map<String, String> map = new HashMap<>();
        map.put("0", "请求成功");
        map.put("-1001", "缺少账户(account)参数");
        map.put("-1002", "缺少时间签(ts)参数");
        map.put("-1003", "缺少令牌(token)参数");
        map.put("-1004", "非法的时间签(ts)");
        map.put("-1005", "时间签(ts)已过期");
        map.put("-1006", "非法的令牌(token)");
        map.put("-1007", "鉴权失败");
        map.put("-1008", "非法的源IP地址");
        map.put("-2001", "账户余额不足");
        map.put("-2002", "账户被禁用");
        map.put("-2003", "账户不在服务时间内");
        map.put("-2101", "账户未配置产品");
        map.put("-2102", "产品已暂停");
        map.put("-2103", "产品不在服务时间内");
        map.put("-2104", "非法的产品");
        map.put("-2201", "通道未配置");
        map.put("-2202", "通道不在服务时间内");
        map.put("-2301", "缺少目标号码(dest)参数");
        map.put("-2302", "非法的目标号码(dest)");
        map.put("-2303", "单次请求号码包大于500个");
        map.put("-2401", "非法的扩展号(ext)");
        map.put("-2501", "缺乏内容(content)参数");
        map.put("-2502", "内容长度超过限制");
        map.put("-2503", "内容缺少签名");
        map.put("-2504", "内容含有禁止发送的关键字");
        map.put("-2505", "个性化短信的手机号码和内容数不一致");
        map.put("-5001", "加载所有运营商数据为空");
        map.put("-5002", "加载所有账户为空");
        map.put("-2010", "拉取过于频繁");
        map.put("success", "操作成功"); // 建议根据实际业务区分“上行推送成功”和“状态报告推送成功”
        ERROR_MESSAGE_MAP = Collections.unmodifiableMap(map);
    }

    /**
     * 结果的状态码，为“0”则为成功，非零值代表错误。请参考文档尾部的具体返回值含义表。
     */
    private String status;

    /**
     * 除非特殊说明，data变量总是一个JSON对象，为返回的数据域内容。
     */
    private String data;

    /**
     * 额外的错误信息字段，可能用于运营商返回的错误。
     */
    private String errorMsdn;

    public boolean isSuccess() {
        return "0".equals(status);
    }

    public String getErrorMessage() {
        return ERROR_MESSAGE_MAP.getOrDefault(status, "未知错误（代码：" + status + "）");
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getErrorMsdn() {
        return errorMsdn;
    }

    public void setErrorMsdn(String errorMsdn) {
        this.errorMsdn = errorMsdn;
    }
}

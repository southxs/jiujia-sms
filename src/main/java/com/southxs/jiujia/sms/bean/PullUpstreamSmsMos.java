package com.southxs.jiujia.sms.bean;

import java.io.Serializable;

/**
 * UpstreamSmsResponse.java
 * 响应体，短信上行拉取
 *
 * @author Mr.vivion
 * @date 2025/5/20
 */
public class PullUpstreamSmsMos implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 短信发送的目标长号码。
     */
    private String dest;
    /**
     * 发送短信的手机号码
     */
    private String src;
    /**
     * 上行短信内容。（UTF-8编码以后做了URLEncode）
     */
    private String content;
    /**
     * 上行短信接收时间。
     */
    private String date;
    /**
     * 消息唯一id(需配置)。
     */
    private String msgId;
    /**
     * 客户可以对提交的短信加入reference以便后续进行跟踪，设置的ref参数最终会随短信状态报告同步给客户。
     */
    private String ref;

    public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }
}

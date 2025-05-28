package com.southxs.jiujia.sms.bean;

/**
 * SmsSendBean.java
 *
 * @author Mr.vivion
 * @date 2025/5/6
 */
public class SendSmsRequest extends SmsBean {

    /**
     * 短信发送的目标手机号码，如果要批量发送短信，多个手机号码以半角逗号“,”分隔开
     */
    private String dest;

    /**
     * 短信内容 URLEncode(content_1) + “|||” + URLEncode(content_2)；
     * 即将各内容进行URLEncode编码后以连接符“|||”（三个连续的管道符）进行连接即可。
     */
    private String content;

    /**
     * 客户可以对提交的短信加入reference以便后续进行跟踪，设置的ref参数最终会随短信状态报告同步给客户。
     */
    private String ref;

    /**
     * 自定义的扩展号，将会出现在发送方号码的尾部。
     */
    private String ext;


    public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }
}

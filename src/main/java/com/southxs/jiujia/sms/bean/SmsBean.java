package com.southxs.jiujia.sms.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * SmsConfig.java
 *
 * @author Mr.vivion
 * @date 2025/5/6
 */
public class SmsBean implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 短消息平台分配的业务账号，一般为6位的字母或数字。账号主要用来区分业务端口及出具对账数据。
     */
    private String account;
    /**
     * 密码
     */
    private String password;
    /**
     * 时间戳
     */
    private String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}

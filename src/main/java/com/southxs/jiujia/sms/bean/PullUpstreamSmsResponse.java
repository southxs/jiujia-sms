package com.southxs.jiujia.sms.bean;

import java.io.Serializable;
import java.util.List;

/**
 * UpstreamSmsResponse.java
 * 响应体，短信上行拉取
 *
 * @author Mr.vivion
 * @date 2025/5/20
 */
public class PullUpstreamSmsResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 短信上行数据。
     */
    private List<PullUpstreamSmsMos> mos;

    public List<PullUpstreamSmsMos> getMos() {
        return mos;
    }

    public void setMos(List<PullUpstreamSmsMos> mos) {
        this.mos = mos;
    }
}

package com.wq.common.model;

/**
 * 推送消息内容
 * Created by WQ on 2017/6/26.
 */

public class PushMessage {

    /**
     * type : 1
     * template_id : 16
     */

    private int type;
    private String template_id;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(String template_id) {
        this.template_id = template_id;
    }
}

package com.wq.common.model;

import java.util.List;

/**
 * Created by Zheng on 2017/7/2.
 */

public class ToBeForwardedObj {


    /**
     * remind_id : 23
     * template_id : 7
     * add_time : 2017-05-28 01:59:27
     */

    private String remind_id;
    private String template_id;
    private String add_time;
    private String template_name;

    public String getTemplate_name() {
        return template_name;
    }

    public void setTemplate_name(String template_name) {
        this.template_name = template_name;
    }

    public String getRemind_id() {
        return remind_id;
    }

    public void setRemind_id(String remind_id) {
        this.remind_id = remind_id;
    }

    public String getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(String template_id) {
        this.template_id = template_id;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

}

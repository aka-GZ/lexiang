package com.wq.common.model;

/**
 * Created by cnsunrun on 2017/5/26.
 */

public class TeamTemplateListObj {
    /**
     * @return template_id 模板ID
     * @return template_name 模板名称
     * @return class_id 模板分类ID
     * @return template_cover_img_url 模板封面图片路径
     * @return template_add_time 模板添加时间
     * @return forwarded 已转发团队成员list
     * @return not_forward 未转发团队成员list
     * @return forward_expiration_date 模板转发截止时间
     * @return uid 模板所属用户uid
     * @return founder_forwarding_times 模板创建人转发次数
     */

    private String template_id;
    private String template_name;
    private Object class_id;
    private String template_cover_img_url;
    private String template_add_time;
    private String forwarded;
    private String not_forward;
    private String forward_expiration_date;
    private String uid;
    private String founder_forwarding_times;

    public String getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(String template_id) {
        this.template_id = template_id;
    }

    public String getTemplate_name() {
        return template_name;
    }

    public void setTemplate_name(String template_name) {
        this.template_name = template_name;
    }

    public Object getClass_id() {
        return class_id;
    }

    public void setClass_id(Object class_id) {
        this.class_id = class_id;
    }

    public String getTemplate_cover_img_url() {
        return template_cover_img_url;
    }

    public void setTemplate_cover_img_url(String template_cover_img_url) {
        this.template_cover_img_url = template_cover_img_url;
    }

    public String getTemplate_add_time() {
        return template_add_time;
    }

    public void setTemplate_add_time(String template_add_time) {
        this.template_add_time = template_add_time;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getForwarded() {
        return forwarded;
    }

    public void setForwarded(String forwarded) {
        this.forwarded = forwarded;
    }

    public String getNot_forward() {
        return not_forward;
    }

    public void setNot_forward(String not_forward) {
        this.not_forward = not_forward;
    }

    public String getForward_expiration_date() {
        return forward_expiration_date;
    }

    public void setForward_expiration_date(String forward_expiration_date) {
        this.forward_expiration_date = forward_expiration_date;
    }

    public String getFounder_forwarding_times() {
        return founder_forwarding_times;
    }

    public void setFounder_forwarding_times(String founder_forwarding_times) {
        this.founder_forwarding_times = founder_forwarding_times;
    }
}

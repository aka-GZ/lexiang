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
     * @return uid 模板所属用户uid
     */

    private String template_id;
    private String template_name;
    private Object class_id;
    private String template_cover_img_url;
    private String template_add_time;
    private String uid;

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
}

package com.wq.common.model;

import java.util.List;

/**
 * Created by cnsunrun on 2017/5/26.
 */

public class TeamTemplateListObj {
    /**
     * template_id : 44
     * template_name : 搞笑段子
     * class_id : 0
     * template_content : 毫无违和感，哈哈哈哈，又躺枪了🤣
     * uid : 2503
     * is_open : 1
     * use_num : 0
     * template_price : 0.00
     * is_member_free : -1
     * template_add_time : 2017-07-04 10:40:51
     * forwarding_times : 5
     * template_cover_img_url : http://wxyx.lyfz.net/Public/Uploads/Model/Cube/2017-07-04/5qlR7P3ME40q2mTI.png
     * is_shop : 1
     * is_recommend : -1
     * founder_forwarding_times : 0
     * forwarded : []
     * not_forward : []
     */

    public String template_id;
    public String template_name;
    private String class_id;
    private String template_content;
    private String uid;
    private String is_open;
    private String use_num;
    private String template_price;
    private String is_member_free;
    private String template_add_time;
    private String forwarding_times;
    private String template_cover_img_url;
    private String is_shop;
    private String is_recommend;
    private String founder_forwarding_times;
    private List<TeamTemplateForwarded> forwarded;
    private List<TeamTemplateNotForwarded> not_forward;

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

    public String getClass_id() {
        return class_id;
    }

    public void setClass_id(String class_id) {
        this.class_id = class_id;
    }

    public String getTemplate_content() {
        return template_content;
    }

    public void setTemplate_content(String template_content) {
        this.template_content = template_content;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getIs_open() {
        return is_open;
    }

    public void setIs_open(String is_open) {
        this.is_open = is_open;
    }

    public String getUse_num() {
        return use_num;
    }

    public void setUse_num(String use_num) {
        this.use_num = use_num;
    }

    public String getTemplate_price() {
        return template_price;
    }

    public void setTemplate_price(String template_price) {
        this.template_price = template_price;
    }

    public String getIs_member_free() {
        return is_member_free;
    }

    public void setIs_member_free(String is_member_free) {
        this.is_member_free = is_member_free;
    }

    public String getTemplate_add_time() {
        return template_add_time;
    }

    public void setTemplate_add_time(String template_add_time) {
        this.template_add_time = template_add_time;
    }

    public String getForwarding_times() {
        return forwarding_times;
    }

    public void setForwarding_times(String forwarding_times) {
        this.forwarding_times = forwarding_times;
    }

    public String getTemplate_cover_img_url() {
        return template_cover_img_url;
    }

    public void setTemplate_cover_img_url(String template_cover_img_url) {
        this.template_cover_img_url = template_cover_img_url;
    }

    public String getIs_shop() {
        return is_shop;
    }

    public void setIs_shop(String is_shop) {
        this.is_shop = is_shop;
    }

    public String getIs_recommend() {
        return is_recommend;
    }

    public void setIs_recommend(String is_recommend) {
        this.is_recommend = is_recommend;
    }

    public String getFounder_forwarding_times() {
        return founder_forwarding_times;
    }

    public void setFounder_forwarding_times(String founder_forwarding_times) {
        this.founder_forwarding_times = founder_forwarding_times;
    }

    public List<TeamTemplateForwarded> getForwarded() {
        return forwarded;
    }

    public void setForwarded(List<TeamTemplateForwarded> forwarded) {
        this.forwarded = forwarded;
    }

    public List<TeamTemplateNotForwarded> getNot_forward() {
        return not_forward;
    }

    public void setNot_forward(List<TeamTemplateNotForwarded> not_forward) {
        this.not_forward = not_forward;
    }
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






//
//    public String template_id;
//    public String template_name;
//    private Object class_id;
//    private String template_cover_img_url;
//    private String template_add_time;
//    private List<TeamTemplateForwarded> forwarded;
//    private List<TeamTemplateNotForwarded> not_forward;
//    private String forward_expiration_date;
//    private String uid;
//    private String founder_forwarding_times;
//
//    public String getTemplate_id() {
//        return template_id;
//    }
//
//    public void setTemplate_id(String template_id) {
//        this.template_id = template_id;
//    }
//
//    public String getTemplate_name() {
//        return template_name;
//    }
//
//    public void setTemplate_name(String template_name) {
//        this.template_name = template_name;
//    }
//
//    public Object getClass_id() {
//        return class_id;
//    }
//
//    public void setClass_id(Object class_id) {
//        this.class_id = class_id;
//    }
//
//    public String getTemplate_cover_img_url() {
//        return template_cover_img_url;
//    }
//
//    public void setTemplate_cover_img_url(String template_cover_img_url) {
//        this.template_cover_img_url = template_cover_img_url;
//    }
//
//    public String getTemplate_add_time() {
//        return template_add_time;
//    }
//
//    public void setTemplate_add_time(String template_add_time) {
//        this.template_add_time = template_add_time;
//    }
//
//    public String getUid() {
//        return uid;
//    }
//
//    public void setUid(String uid) {
//        this.uid = uid;
//    }
//
//    public List<TeamTemplateForwarded> getForwarded() {
//        return forwarded==null?new ArrayList<TeamTemplateForwarded>():forwarded;
//    }
//
//    public void setForwarded(List<TeamTemplateForwarded> forwarded) {
//        this.forwarded = forwarded;
//    }
//
//    public List<TeamTemplateNotForwarded> getNot_forward() {
//        return not_forward==null?new ArrayList<TeamTemplateNotForwarded>():not_forward;
//    }
//
//    public void setNot_forward(List<TeamTemplateNotForwarded> not_forward) {
//        this.not_forward = not_forward;
//    }
//
//    public String getForward_expiration_date() {
//        return forward_expiration_date;
//    }
//
//    public void setForward_expiration_date(String forward_expiration_date) {
//        this.forward_expiration_date = forward_expiration_date;
//    }
//
//    public String getFounder_forwarding_times() {
//        return founder_forwarding_times;
//    }
//
//    public void setFounder_forwarding_times(String founder_forwarding_times) {
//        this.founder_forwarding_times = founder_forwarding_times;
//    }
}

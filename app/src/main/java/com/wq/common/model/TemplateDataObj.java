package com.wq.common.model;

import java.util.List;

/**
 * Created by Zheng on 2017/6/11.
 */

public class TemplateDataObj {


    /**
     * template_name : u6d4bu8bd5777
     * template_content : u6d4bu8bd5u518522u5bb9
     * template_add_time : 2017-05-26 05:38:51
     * is_member_free : -1
     * template_cover_img_url : http://localhost:81/Public/Uploads/Model/Cube/2017-05-26/7sjhu0tyhGYkFjnM.jpeg
     * is_open : -1
     * template_id : 3
     * imgList : [{"template_img_id":"5","img_url":"http://localhost:81/Public/Uploads/Model/Cube/2017-05-26/MugJv16lz0U87zGD.jpeg","img_order":"0"},{"template_img_id":"6","img_url":"http://localhost:81/Public/Uploads/Model/Cube/2017-05-26/pcSC0o140M773qa3.jpeg","img_order":"1"}]
     * remindPeopleList : ["42","45"]
     */

    private String template_name;
    private String template_content;
    private String template_add_time;
    private String is_member_free;
    private String template_cover_img_url;
    private String is_open;
    private String template_id;
    private List<ImgListBean> imgList;
    private List<String> remindPeopleList;

    public String getTemplate_name() {
        return template_name;
    }

    public void setTemplate_name(String template_name) {
        this.template_name = template_name;
    }

    public String getTemplate_content() {
        return template_content;
    }

    public void setTemplate_content(String template_content) {
        this.template_content = template_content;
    }

    public String getTemplate_add_time() {
        return template_add_time;
    }

    public void setTemplate_add_time(String template_add_time) {
        this.template_add_time = template_add_time;
    }

    public String getIs_member_free() {
        return is_member_free;
    }

    public void setIs_member_free(String is_member_free) {
        this.is_member_free = is_member_free;
    }

    public String getTemplate_cover_img_url() {
        return template_cover_img_url;
    }

    public void setTemplate_cover_img_url(String template_cover_img_url) {
        this.template_cover_img_url = template_cover_img_url;
    }

    public String getIs_open() {
        return is_open;
    }

    public void setIs_open(String is_open) {
        this.is_open = is_open;
    }

    public String getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(String template_id) {
        this.template_id = template_id;
    }

    public List<ImgListBean> getImgList() {
        return imgList;
    }

    public void setImgList(List<ImgListBean> imgList) {
        this.imgList = imgList;
    }

    public List<String> getRemindPeopleList() {
        return remindPeopleList;
    }

    public void setRemindPeopleList(List<String> remindPeopleList) {
        this.remindPeopleList = remindPeopleList;
    }

    public static class ImgListBean {
        /**
         * template_img_id : 5
         * img_url : http://localhost:81/Public/Uploads/Model/Cube/2017-05-26/MugJv16lz0U87zGD.jpeg
         * img_order : 0
         */

        private String template_img_id;
        private String img_url;
        private String img_order;

        public String getTemplate_img_id() {
            return template_img_id;
        }

        public void setTemplate_img_id(String template_img_id) {
            this.template_img_id = template_img_id;
        }

        public String getImg_url() {
            return img_url;
        }

        public void setImg_url(String img_url) {
            this.img_url = img_url;
        }

        public String getImg_order() {
            return img_order;
        }

        public void setImg_order(String img_order) {
            this.img_order = img_order;
        }
    }
}

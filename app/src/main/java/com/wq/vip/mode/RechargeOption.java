package com.wq.vip.mode;

/**
 * 充值选项
 * Created by weiquan on 2017/6/11.
 */

public class RechargeOption {

    /**
     * option_id : 1
     * option_name : 20u6210u5458u4f1au5458
     * day : 365
     * price : 30
     * group_num : 500
     */

    public String option_id;
    public String option_name;
    public String day;
    public String price;
    public String group_num;

    public String getOption_id() {
        return option_id;
    }

    public void setOption_id(String option_id) {
        this.option_id = option_id;
    }

    public String getOption_name() {
        return option_name;
    }

    public void setOption_name(String option_name) {
        this.option_name = option_name;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getGroup_num() {
        return group_num;
    }

    public void setGroup_num(String group_num) {
        this.group_num = group_num;
    }
}

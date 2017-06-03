package com.wq.common.model;

/**
 * Created by cnsunrun on 2017/5/26.
 */

public class LoginInfo {
    /**
     * user_money : 0.00
     * phone_no : 15916035572
     * email : null
     * login_time : 2017-05-25 15:57:51
     * token : be1c5c3fdedd1d3b0f692db5555f4dd5
     */

    private String user_money;
    private String phone_no;
    private Object email;
    private String login_time;
    private String token;



    public String getUser_money() {
        return user_money;
    }

    public void setUser_money(String user_money) {
        this.user_money = user_money;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public Object getEmail() {
        return email;
    }

    public void setEmail(Object email) {
        this.email = email;
    }

    public String getLogin_time() {
        return login_time;
    }

    public void setLogin_time(String login_time) {
        this.login_time = login_time;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "LoginInfo{" +
                "user_money='" + user_money + '\'' +
                ", phone_no='" + phone_no + '\'' +
                ", email=" + email +
                ", login_time='" + login_time + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
